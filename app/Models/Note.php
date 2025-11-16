<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Model;

// note belongs to one user and one category
use Illuminate\Database\Eloquent\Relations\BelongsTo;

class Note extends Model
{
    protected $fillable = [
        'title',
        'content',
        'is_archived',
        'is_deleted',
        'users_id', // foregin key to Users
        'categories_id', // foreign key to Categories
    ];

    /**
     * Note milik satu User (Belongs To)
     */
    public function user(): BelongsTo
    {
        // terhubung ke Model User melalui 'users_id'
        return $this->belongsTo(User::class, 'users_id');
    }

    /**
     * Note milik satu Category (Belongs To)
     */
    public function category(): BelongsTo
    {
        // terhubung ke Model Category melalui 'categories_id'
        return $this->belongsTo(Category::class, 'categories_id');
    }
}
