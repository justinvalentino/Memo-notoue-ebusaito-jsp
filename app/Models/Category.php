<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Model;
// category belongs to many notes
use Illuminate\Database\Eloquent\Relations\HasMany;

class Category extends Model
{
    protected $fillable = ['name', 'user_id'];

    public function notes(): HasMany
    {
        // terhubung ke Model Note melalui kolom 'categories_id'
        return $this->hasMany(Note::class, 'categories_id');
    }
}
