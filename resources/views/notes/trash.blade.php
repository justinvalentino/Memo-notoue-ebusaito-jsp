<x-app-layout>
    <div class="max-w-7xl mx-auto py-10 sm:px-6 lg:px-8">

        <div class="bg-white dark:bg-gray-800 overflow-hidden shadow-xl sm:rounded-lg p-6 mb-8 border border-transparent dark:border-gray-700 dark:text-gray-300">
            <h1 class="text-3xl font-bold text-gray-800 dark:text-gray-200">
                Trash Bin 
            </h1>
            <p class="mt-2 text-gray-600 dark:text-gray-400">
                Notes here can be restored to your main list or permanently deleted.
            </p>
        </div>
        <!-- back button -->
        <div class="flex justify-end mb-6">
            <a href="{{ route('notes.index') }}" class="bg-gray-400 hover:bg-gray-500 text-white font-bold py-2 px-4 rounded-md transition duration-150 ease-in-out">
                Back to Notes
            </a>
        </div>

        <div class="space-y-4">
            @forelse ($notes as $note)
                <div class="bg-white dark:bg-gray-800 p-4 shadow-md rounded-lg border-l-4 border-red-500 dark:border-red-600">
                
                    <div class="flex justify-between items-start">
                        <h2 class="text-xl font-semibold text-gray-900 dark:text-gray-200 mr-4 break-words">{{ $note->title }}</h2>
                        <div class="flex space-x-2 flex-shrink-0">
                            
                            <!-- Restore button -->
                            <form action="{{ route('notes.restore', $note) }}" method="POST" onsubmit="return confirm('Are you sure you want to restore this note?');">
                                @csrf
                                @method('PATCH')
                                <button type="submit" class="
                                    bg-green-500 hover:bg-green-600 text-white 
                                    font-bold py-1 px-3 text-sm 
                                    rounded-md transition duration-150 ease-in-out">
                                    Restore
                                </button>
                            </form>

                            <!-- Permanent delete button -->
                            <form action="{{ route('notes.destroy', $note) }}" method="POST" onsubmit="return confirm('Are you sure you want to permanently delete this note? This action cannot be undone!');">
                                @csrf
                                @method('DELETE')
                                <button type="submit" class="
                                    bg-red-600 hover:bg-red-700 text-white 
                                    font-bold py-1 px-3 text-sm 
                                    rounded-md transition duration-150 ease-in-out">
                                    Delete Permanently
                                </button>
                            </form>
                        </div>
                    </div>
                    <!-- to see when note last updated -->
                    <p class="mt-2 text-gray-600 dark:text-gray-400">{{ Str::limit($note->body, 150) }}</p>
                    <p class="mt-3 text-xs text-gray-500 dark:text-gray-500">
                        Archived: {{ $note->updated_at->diffForHumans() }}
                    </p>
                </div>
            @empty
            <!--If empty-->
                <p class="text-center text-gray-500 dark:text-gray-400 py-10">The trash bin is empty!</p>
            @endforelse
        </div>
    </div>
</x-app-layout>