<?php

use App\Http\Controllers\ProfileController;
use App\Http\Controllers\NoteController;        // importing NoteController
use App\Http\Controllers\CategoryController;
use Illuminate\Support\Facades\Route;

Route::get('/', function () {
    return view('/auth/login');
});

Route::get('/dashboard', function () {
    return redirect()->route('notes.index');        // redirect to a display of notes for the Dashboard
})->middleware(['auth', 'verified'])->name('dashboard');

Route::middleware('auth')->group(function () {
    Route::get('/profile', [ProfileController::class, 'edit'])->name('profile.edit');
    Route::patch('/profile', [ProfileController::class, 'update'])->name('profile.update');
    Route::delete('/profile', [ProfileController::class, 'destroy'])->name('profile.destroy');
    
    //for archive
    Route::patch('/notes/{note}/archive', [NoteController::class, 'archive'])->name('notes.archive');
    Route::get('/notes/trash', [NoteController::class, 'trash'])->name('notes.trash');
    Route::patch('/notes/{note}/restore', [NoteController::class, 'restore'])->name('notes.restore');
    
    //for categories
    Route::get('/categories', [CategoryController::class, 'index'])->name('categories.index');
    Route::post('/categories', [CategoryController::class, 'store'])->name('categories.store');
    Route::delete('/categories/{category}', [CategoryController::class, 'destroy'])->name('categories.destroy');

    Route::resource('notes', NoteController::class)->names([
        'index' => 'notes.index',
        'create' => 'notes.create',
        'store' => 'notes.store',
        'show' => 'notes.show',
        'edit' => 'notes.edit',
        'update' => 'notes.update',
        'destroy' => 'notes.destroy',
    ]);

});


require __DIR__.'/auth.php';
