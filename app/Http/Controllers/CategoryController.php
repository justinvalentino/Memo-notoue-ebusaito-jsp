<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use App\Models\Category;

class CategoryController extends Controller
{
    public function index()
    {
        $categories = Category::where('user_id', auth()->id())->get();
        return view('categories.index', compact('categories'));
    }


    public function destroy(Category $category)
    {
        if ($category->user_id != auth()->id()) {
            abort(403, 'Unauthorized action.');
        }

        $category->notes()->update(['categories_id' => null]);
        $category->delete();

        return redirect()->route('categories.index')->with('success', 'Category "' . $category->name . '" has been successfully deleted.');
    }

    public function store(Request $request)
    {
        $request->validate([
            'name' => 'required|string|max:255|unique:categories,name',
        ]);
        
        $categoryName = ucwords(trim($request->name));

        //make the category user specific
        \App\Models\Category::create([
            'name' => $categoryName,
            'user_id' => auth()->id(), 
        ]);

        return redirect()->route('categories.index')->with('success', 'Category "' . $categoryName . '" has been successfully created!');
    }
}
