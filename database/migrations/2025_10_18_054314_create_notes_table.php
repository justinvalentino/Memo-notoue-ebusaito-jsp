<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

return new class extends Migration
{
    /**
     * Run the migrations.
     */
    public function up(): void
    {
        Schema::create('notes', function (Blueprint $table) {
            $table->id(); // primary key

            // Data Columns
            $table->string('title', 255); // varchar title
            $table->longText('content')->nullable(); // content, longtext (dibuat nullable, karena mungkin ada catatan kosong)
            $table->boolean('is_archived')->default(false); // is_archived, boolean
            $table->boolean('is_deleted')->default(false); // is_deleted, boolean

            // Foreign Key (FK) to User
            // Menggunakan unsignedBigInteger untuk users_id agar sesuai dengan id tabel 'users'
            $table->unsignedBigInteger('users_id');
            $table->foreign('users_id')->references('id')->on('users')->onDelete('cascade'); // Jika user dihapus, note juga dihapus.

            // Foreign Key (FK) to Category
            // Menggunakan unsignedBigInteger, id Laravel defaultnya bigint.
            // Dibuat nullable karena note mungkin tidak punya categories_id.
            $table->unsignedBigInteger('categories_id')->nullable(); 
            $table->foreign('categories_id')->references('id')->on('categories')->onDelete('set null'); // Jika category dihapus, kolom categories_id di note diatur ke NULL.

            // Timestamps
            $table->timestamps(); // created_at, updated_at
        });
    }

    /**
     * Reverse the migrations.
     */
    public function down(): void
    {
        Schema::dropIfExists('notes');
    }
};
