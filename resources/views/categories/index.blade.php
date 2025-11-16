<x-app-layout>
    <x-slot name="header">
        <h2 class="font-semibold text-xl text-gray-800 dark:text-gray-200 leading-tight">
            Manage Categories
        </h2>
    </x-slot>

    <div class="py-12">
        <div class="max-w-4xl mx-auto sm:px-6 lg:px-8">
            <div class="bg-white dark:bg-gray-800 overflow-hidden shadow-xl sm:rounded-lg p-6 lg:p-8">

                <h3 class="text-lg font-medium text-gray-900 dark:text-gray-100 mb-4">Create New Category</h3>
                <form action="{{ route('categories.store') }}" method="POST" class="mb-8">
                    @csrf
                    <div class="flex items-center space-x-2">
                        <input 
                            type="text" 
                            name="name" 
                            value="{{ old('name') }}"
                            placeholder="Enter category name"
                            required
                            class="w-full border-gray-300 dark:border-gray-700 dark:bg-gray-900 dark:text-gray-300 rounded-lg shadow-sm focus:border-blue-500 dark:focus:border-blue-600 focus:ring-blue-500 dark:focus:ring-blue-600"
                        />
                        <button type="submit" class="inline-flex items-center px-4 py-2 bg-green-600 border border-transparent rounded-lg font-semibold text-xs text-white uppercase tracking-widest hover:bg-green-700 transition ease-in-out duration-150">
                            +
                        </button>
                    </div>
                    @error('name')
                        <p class="text-sm text-red-600 dark:text-red-400 mt-2">{{ $message }}</p>
                    @enderror
                </form>

                <hr class="my-6 border-gray-200 dark:border-gray-700" />

                <h3 class="text-lg font-medium text-gray-900 dark:text-gray-100 mb-4">Existing Categories</h3>

                @if (session('success'))
                    <div class="p-4 mb-4 text-sm text-green-700 bg-green-100 rounded-lg dark:bg-green-700 dark:text-green-100" role="alert">
                        {{ session('success') }}
                    </div>
                @endif

                <div class="space-y-4">
                    @forelse ($categories as $category)
                        <div class="flex items-center justify-between p-3 bg-gray-50 dark:bg-gray-700 rounded-lg shadow-sm">
                            <span class="text-gray-900 dark:text-gray-100 font-semibold">
                                {{ $category->name }}
                                <span class="text-sm text-gray-500 dark:text-gray-400">
                                    ({{ $category->notes->count() }} notes)
                                </span>
                            </span>

                            {{-- DELETE FORM --}}
                            <form action="{{ route('categories.destroy', $category) }}" method="POST" onsubmit="return confirm('Are you sure you want to delete the category: {{ $category->name }}? All {{ $category->notes->count() }} associated notes will be uncategorized.');">
                                @csrf
                                @method('DELETE')
                                <button type="submit" class="inline-flex items-center px-3 py-1 bg-red-600 border border-transparent rounded-md font-semibold text-xs text-white uppercase tracking-widest hover:bg-red-700 focus:outline-none focus:ring-2 focus:ring-red-500 focus:ring-offset-2 transition ease-in-out duration-150">
                                    Delete
                                </button>
                            </form>
                        </div>
                    @empty
                        <p class="text-gray-500 dark:text-gray-400">No categories found. Start adding some!</p>
                    @endforelse
                </div>

            </div>
        </div>
    </div>
</x-app-layout>