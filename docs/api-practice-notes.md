# API practice notes (Postman, jsonplaceholder)

| Метод  | URL                | Ожидаемый статус | Фактический статус |
|--------|--------------------|------------------|--------------------|
| GET    | /users/1           | 200              | 200                |
| GET    | /users/9999        | 404              | 404                |
| GET    | /posts?userId=1    | 200              | 200                |
| GET    | /posts/1/comments  | 200              | 200                |
| POST   | /posts             | 201              | 201                |
| PUT    | /posts/1           | 200              | 200                |
| PATCH  | /posts/1           | 200              | 200                |
| DELETE | /posts/1           | 200              | 200                |

> jsonplaceholder — fake API: POST/PUT/DELETE не сохраняют данные, поэтому
> после POST нельзя сделать GET созданного поста.
