# graphql-spring-boot

```
**Open URL**:
https://localhost:8080/graphiql

Use below Queries:
mutation {
  createBook(bookName:"CORE JAVA", pages:300, category:COMEDY, age:2, authorName:"RISHABH SHARMA")
}

mutation {
  createBook(bookName:"DSA", pages:300, category:HORROR, age:2, authorName:"RISHABH SHARMA")
}

query {
  books:getBooks{
    id
    name:name
    pages
    author {
      id
      name
      age
    }
    category
  }
}
```

```
Create Book:
curl --location --request POST 'http://localhost:8080/graphql' \
--header 'Content-Type: application/json' \
--data-raw '{"query":"mutation {\n  createBook(bookName:\"DSA\", pages:300, category:HORROR, age:2, authorName:\"RISHABH SHARMA\")\n}","variables":null}'



Create Book2:
curl --location --request POST 'http://localhost:8080/books-graphql' \
--header 'Content-Type: application/json' \
--data-raw '{"query":"mutation {\n  createBook2(bookName:\"DSA\", pages:300, category:HORROR, age:2, authorName:\"RISHABH SHARMA\")\n}","variables":null}'




Get Books:
curl --location --request POST 'http://localhost:8080/graphql' \
--header 'Content-Type: application/json' \
--data-raw '{
    "query": "query {\n  books:getBooks{\n    id\n    first_name:name\n    pages\n    author {\n      id\n      name\n      age\n    }\n    category\n  }\n}",
    "variables": null
}'

 -- OR --

URL:
http://localhost:8080/graphql

Method:
POST

JSON Request Body:
{
    "query": "query {\n  books:getBooks{\n    id\n    first_name:name\n    pages\n    author {\n      id\n      name\n      age\n    }\n    category\n  }\n}",
    "variables": null
}



Get Books2:
curl --location --request POST 'http://localhost:8080/books-graphql' \
--header 'Content-Type: application/json' \
--data-raw '{
    "query": "query {\n  books:getBooks2{\n    id\n    first_name:name\n    pages\n    author {\n      id\n      name\n      age\n    }\n    category\n  }\n}",
    "variables": null
}'

 -- OR --

URL:
http://localhost:8080/books-graphql

Method:
POST

JSON Request Body:
{
    "query": "query {\n  books:getBooks2{\n    id\n    first_name:name\n    pages\n    author {\n      id\n      name\n      age\n    }\n    category\n  }\n}",
    "variables": null
}
```

[comment]: <> (Links/Tutorials)

[comment]: <> (```)

[comment]: <> (GraphQL tutorial for beginners - Mastering your API security with Spring-boot in seconds : Part 2/3:)

[comment]: <> (https://www.youtube.com/watch?v=1PKqY50IjBw)

[comment]: <> (Spring Boot GraphQL Tutorial:)

[comment]: <> (https://www.youtube.com/watch?v=JEe8FUCz4VY)

[comment]: <> (https://github.com/pravintarte/graphql)

[comment]: <> (GraphQL Spring Boot #8 - Executing a Query:)

[comment]: <> (https://www.youtube.com/watch?v=rHIRRDZ0WKM)

[comment]: <> (Fetching Data using GraphQL Queries with React Hooks and Axios:)

[comment]: <> (https://www.youtube.com/watch?v=NbKJFRgsw-A)

[comment]: <> (```)