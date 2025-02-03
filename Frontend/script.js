document.addEventListener('DOMContentLoaded', function () {
    // Check if the user is logged in by reading from localStorage
    const isLoggedIn = localStorage.getItem('isLoggedIn');
    console.log('isLoggedIn:', isLoggedIn); // Debugging line to check if the value is correct

    // If user is logged in, enable the custom short URL field and show the URL form container
    if (isLoggedIn === 'true') {
        console.log('User is logged in. Enabling custom short URL feature.');
        document.getElementById('custom-url').disabled = false; // Enable the custom short URL field
        document.getElementById('url-form-container').style.display = 'block'; // Show the form
    } else {
        console.log('User is not logged in. Redirecting to login page.');
    }

    // Handle the URL shortening form submission
    document.getElementById('url-form').addEventListener('submit', async function (event) {
        event.preventDefault(); // Prevent default form submission

        const longUrl = document.getElementById('long-url').value;
        const customShortUrl = document.getElementById('custom-url').value;

        const requestBody = {
            longUrl: longUrl,
            customShortUrl: customShortUrl,
        };

        try {
            const response = await fetch("http://localhost:8080/shorten", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify(requestBody),
            });

            if (response.ok) {
                const result = await response.text(); // Receive short URL as text

                // Display the shortened URL in the result section
                document.getElementById("result").style.display = "block";
                document.getElementById("short-url").innerHTML = `http://localhost:8080/${result}`;
                document.getElementById("error").style.display = "none";

                // Handle copy functionality
                const copyButton = document.getElementById("copy-btn");
                copyButton.addEventListener("click", function() {
                    const shortUrl = `http://localhost:8080/${result}`;
                    navigator.clipboard.writeText(shortUrl).then(function() {
                        alert('Short URL copied to clipboard!');
                    }).catch(function() {
                        alert('Failed to copy short URL!');
                    });
                });

            } else {
                const error = await response.text();
                document.getElementById("error").style.display = "block";
                document.getElementById("error-message").innerText = error;
            }
        } catch (error) {
            document.getElementById("error").style.display = "block";
            document.getElementById("error-message").innerText = "Error creating short URL. Please try again.";
        }
    });
});


function logout() {
    // Remove the 'isLoggedIn' flag from localStorage
    localStorage.removeItem('isLoggedIn');

    // Redirect to the login page
    alert("u logged out")
}