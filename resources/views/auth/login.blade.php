<x-guest-layout>
    <x-auth-session-status class="mb-4" :status="session('status')" />
    
    <form method="POST" action="{{ route('login') }}">
        @csrf

        {{-- Welcome Message Section --}}
        <div class="text-center mb-6">
            <h1 class="pt-5 text-3xl font-bold text-gray-800 dark:text-gray-200">
                Welcome Back!
            </h1>
            <p class="text-sm text-gray-600 dark:text-gray-400 mt-1">
                Log in to access your notes on MEMO.
            </p>
        </div>

        {{-- Email Address --}}
        <div class="mt-4">
            <x-input-label for="email" :value="__('Email')" />
            <x-text-input id="email" class="block mt-1 w-full" type="email" name="email" :value="old('email')" required autofocus autocomplete="username" />
            <x-input-error :messages="$errors->get('email')" class="mt-2" />
        </div>

        {{-- Password --}}
        <div class="mt-4">
            <x-input-label for="password" :value="__('Password')" />

            <x-text-input id="password" class="block mt-1 w-full"
                            type="password"
                            name="password"
                            required autocomplete="current-password" />

            <x-input-error :messages="$errors->get('password')" class="mt-2" />
        </div>

        {{-- Remember Me and Register Link (Combined in a row for better flow) --}}
        <div class="flex justify-between items-center mt-6">
            
            {{-- Remember Me --}}
            <label for="remember_me" class="inline-flex items-center">
                <input id="remember_me" type="checkbox" class="rounded dark:bg-gray-900 border-gray-300 dark:border-gray-700 text-blue-600 shadow-sm focus:ring-blue-500 dark:focus:ring-blue-600 dark:focus:ring-offset-gray-800" name="remember">
                <span class="ms-2 text-sm text-gray-600 dark:text-gray-400">{{ __('Remember me') }}</span>
            </label>

            {{-- New User Link --}}
            <span class="text-sm text-gray-600 dark:text-gray-400">
                New user? 
                <a href="{{ route('register') }}" class="font-semibold text-blue-600 dark:text-blue-400 hover:text-blue-500 dark:hover:text-blue-300 underline ms-1">
                    Register Here!
                </a>
            </span>
        </div>

        {{-- Login Button and Forgot Password Link --}}
        <div class="flex items-center justify-end mt-6">
            @if (Route::has('password.request'))
                <a class="underline text-sm text-gray-600 dark:text-gray-400 hover:text-gray-900 dark:hover:text-gray-100 rounded-md focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-blue-500 dark:focus:ring-offset-gray-800" href="{{ route('password.request') }}">
                    {{ __('Forgot your password?') }}
                </a>
            @endif

            <x-primary-button class="ms-4 bg-blue-600 hover:bg-blue-700">
                {{ __('Log in') }}
            </x-primary-button>
        </div>
    </form>
</x-guest-layout>