<?php

namespace App\Http\Controllers;

use App\Models\Note;
use App\Models\Category;
use App\Http\Controllers\Controller;
use Illuminate\Http\Request;

use App\Http\Requests\NoteRequest;  // added

class NoteController extends Controller
{
    /**
     * Display a listing of the resource.
     */
    public function index(Request $request)
    {
        $notesQuery = auth()->user()->notes()
                     ->where('is_archived', false)
                     ->where('is_deleted', false)
                     ->whereNotNull('title')
                     ->where('title', '!=', '');

        // user type sesuatu di search box
        if ($request->filled('search')) {
            $searchTerm = $request->input('search');
            
            // filterkan query
            $notesQuery->where(function ($query) use ($searchTerm) {
                $query->where('title', 'like', "%{$searchTerm}%")      // search title
                    ->orWhere('content', 'like', "%{$searchTerm}%"); // search content
            });
        }
        
        // list of notes atau filter search result
        $notes = $notesQuery->with('category')->latest()->get();

        $categories = \App\Models\Category::all();
        return view('notes.index', compact('notes', 'categories'));
    }

    /**
     * Show the form for creating a new resource.
     */
    public function create()
    {
        $categories = Category::all();
        return view('notes.create', compact('categories'));
    }

    /**
     * Store a newly created resource in storage.
     */
    public function store(NoteRequest $request)
    {
        $data = $request->validated();
        
        $categoryId = $request->filled('categories_id') ? $request->categories_id : null;

        $data['categories_id'] = $categoryId;
        $data['users_id'] = auth()->id();

        \App\Models\Note::create($data); 

        return redirect()->route('notes.index')->with('success', 'Catatan berhasil ditambahkan!');
    }

    /**
     * Display the specified resource.
     */
    public function show(Note $note)
    {
        // memastikan user login dulu, untuk acess note
        if ($note->users_id !== auth()->id()) {
            abort(403, 'Unauthorized action.');
        }
        
        return view('notes.show', compact('note'));
    }

    /**
     * Show the form for editing the specified resource.
     */
    public function edit(Note $note)
    {
        if ($note->users_id !== auth()->id()) {
            abort(403, 'Unauthorized action.');
        }

        $categories = Category::all();
        return view('notes.edit', compact('note', 'categories'));
    }

    /**
     * Update the specified resource in storage.
     */
    public function update(NoteRequest $request, Note $note)
    {
        if ($note->users_id !== auth()->id()) {
            abort(403, 'Unauthorized action.');
        }
        
        $data = $request->validated();
    
        // save the category
        $data['categories_id'] = $request->filled('categories_id') ? $request->categories_id : null;
    
        // update data dengan data tervalidated
        $note->update($data);

        return redirect()->route('notes.index')->with('success', 'Catatan berhasil diperbarui!');
    }

    /**
     * Remove the specified resource from storage.
     */
    public function destroy(Note $note)
    {
        if ($note->users_id !== auth()->id()) {
            abort(403, 'Unauthorized action.');
        }

            //to delete permanently from trash 
        if ($note->is_archived){
            $note->forceDelete();

            return redirect()->route('notes.trash')->with('success', 'Note deleted permanently.');
        }
        
        // update note ke Trash
        $note->update(['is_deleted' => true]); 
        
        return redirect()->route('notes.index')->with('success', 'Catatan dipindahkan ke TRASH.');
    }

    public function archive(Note $note)
    {
        // authorization: making sure bahwa user memiliki note
        if ($note->users_id !== auth()->id()) {
            abort(403);
        }

        // update status note menjadi archived
        $note->update(['is_archived' => true]);

        return redirect()->route('notes.index')->with('success', 'Note sukses archived');
    }

    public function trash ()
    {
        $archivednotes = auth()->user()->notes()
            ->where('is_archived', true)
            ->latest()
            ->get();

            //to show archived notes in trash
        return view('notes.trash', ['notes' => $archivednotes]);
    }

    public function restore(Note $note){
        
        if ($note->users_id !== auth()->id()) {
            abort(403);
        }
            //to change note back to NOT archived
        $note->update(['is_archived' => false]);

        return redirect()->route('notes.trash')->with('success', 'Notes restored succesfully');
    }

}
