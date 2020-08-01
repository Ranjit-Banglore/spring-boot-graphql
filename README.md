An Example query:
{
	allBooks {
		title
		authors
	}
	book (id : "101") {
		title
	}
}

And expected Result:
{
    "errors": [],
    "data": {
        "allBooks": [
            {
                "title": "How to win Friends",
                "authors": [
                    "Dale Karnegie"
                ]
            },
            {
                "title": "Thinking Big",
                "authors": [
                    "author1",
                    "author2"
                ]
            },
            {
                "title": "Book of clouds",
                "authors": [
                    "Sample books 1234"
                ]
            },
            {
                "title": "Spring in action",
                "authors": [
                    "Tata"
                ]
            },
            {
                "title": "Java",
                "authors": [
                    "Sample books 1234"
                ]
            }
        ],
        "book": {
            "title": "How to win Friends"
        }
    },
    "extensions": null
}

# Changing query:
We can change the query according to how many attributes we need for that book.
