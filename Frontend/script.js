document.getElementById("url-form").addEventListener("submit", async function (event) {
    event.preventDefault();

    const longUrl = document.getElementById("long-url").value;
    const customShortUrl = document.getElementById("custom-url").value;

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
