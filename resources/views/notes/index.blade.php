{{-- Display existing notes with search UI, Create, Edit, and Delete buttons --}}

<x-app-layout>

    <div class="max-w-7xl mx-auto py-10 sm:px-6 lg:px-8">

        <!-- Welcome Message -->
        <div class="bg-white dark:bg-gray-800 
                    overflow-hidden shadow-xl sm:rounded-lg p-6 mb-8 
                    border border-transparent dark:border-gray-700
                    dark:text-gray-300">
            <h1 class="text-3xl font-bold text-gray-800 dark:text-gray-200">
                Welcome, {{ auth()->user()->name }}!
            </h1>
            <p class="mt-2 text-gray-600 dark:text-gray-400">
                Here are your notes.
            </p>
        </div>

        <!-- Search + Buttons -->
        <div class="flex flex-col md:flex-row justify-between items-center space-y-4 md:space-y-0 mb-6">
            <div class="w-full md:w-3/5 md:mr-4">
                <form method="GET" action="{{ route('notes.index') }}">
                    <input 
                        type="text" 
                        name="search"  
                        placeholder="Search notes..." 
                        class="w-full border-gray-300 dark:border-gray-700 dark:bg-gray-900 dark:text-gray-300 rounded-md shadow-sm focus:border-blue-500 dark:focus:border-blue-600 focus:ring focus:ring-blue-200 dark:focus:ring-blue-600 focus:ring-opacity-50"
                        value="{{ request('search') }}" 
                    >
                </form>
            </div>

            <div class="flex space-x-3 md:justify-end">
                <a href="{{ route('notes.trash') }}" class="bg-gray-500 hover:bg-gray-700 text-white font-bold py-2 px-4 rounded-md transition duration-150 ease-in-out">
                    Trash Bin
                </a>
                <a href="{{ route('notes.create') }}" class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded-md transition duration-150 ease-in-out">
                    Create New Note
                </a>
            </div>
        </div>

        <!-- Notes Listing -->
        <div class="space-y-4">
            @forelse ($notes as $note)
        <a href="{{ route('notes.edit', $note) }}" class="block 
            bg-white dark:bg-gray-800 p-4 shadow-md rounded-lg 
            border-l-4 border-blue-500 dark:border-blue-600 
            hover:shadow-lg hover:border-l-8 transition duration-200 ease-in-out">
            
            <!-- container for button and notes -->
            <div class="flex justify-between items-start mb-2">
                
                <h2 class="text-xl font-semibold text-gray-900 dark:text-gray-200 mr-4 break-words">
                    {{ $note->title }}
                </h2>
                
                <div class="flex space-x-2 flex-shrink-0">
                    
                    <!-- trash button -->
                    <form action="{{ route('notes.archive', $note) }}" method="POST" onsubmit="event.stopPropagation(); return confirm('Move this note to Trash?');">
                        @csrf
                        @method('PATCH')
                        <button type="submit" class="bg-red-600 hover:bg-red-700 text-white font-bold py-1 px-3 text-sm rounded-md transition duration-150 ease-in-out">
                            Trash
                        </button>
                    </form>
                    
                </div>
            </div>

            <!-- show last note updated -->
            <p class="mt-2 text-gray-600 dark:text-gray-400">{{ \Illuminate\Support\Str::limit($note->body, 150) }}</p>
            <p class="mt-3 text-xs text-gray-500 dark:text-gray-500">
                Last updated: {{ $note->updated_at->diffForHumans() }}
            </p>
        </a>
    @empty
        <p class="text-center text-gray-500 dark:text-gray-400 py-10">
            You haven't created any notes yet!
        </p>
    @endforelse
</div>

    </div>

</x-app-layout>
