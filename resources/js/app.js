import './bootstrap';

import Alpine from 'alpinejs';

window.Alpine = Alpine;

Alpine.start();


// Dark Mode Toggle Logic
const button = document.getElementById('theme-toggle');
const html = document.documentElement; // This targets the <html> tag

// 1. Check for saved preference on load
if (localStorage.getItem('theme') === 'dark' || 
   (!('theme' in localStorage) && window.matchMedia('(prefers-color-scheme: dark)').matches)) {
    html.classList.add('dark');
} else {
    html.classList.remove('dark');
}

// 2. Add the click listener to the button
if (button) {
    button.addEventListener('click', () => {
        // Toggle the 'dark' class on the <html> element
        html.classList.toggle('dark');
        
        // Save the user's preference in local storage
        if (html.classList.contains('dark')) {
            localStorage.setItem('theme', 'dark');
        } else {
            localStorage.setItem('theme', 'light');
        }
    });
}