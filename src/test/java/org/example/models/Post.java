package org.example.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * POJO для /posts. Используется и для отправки (сериализация в JSON),
 * и для чтения ответа (десериализация: response.as(Post.class)).
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Post {
    private Integer id;
    private String title;
    private String body;
    private int userId;

    // Пустой конструктор нужен Jackson для десериализации
    public Post(){}

    //для создания
    public Post(String title, String body, int userId) {
        this.title = title;
        this.body = body;
        this.userId = userId;
    }

    public Integer getId(){return id;}
    public String getTitle(){return title; }
    public String getBody(){return body;}
    public int getUserId(){return userId;}

    public void setId(Integer id){this.id = id;}
    public void setTitle(String title){this.title = title;}
    public void setBody(String body){this.body=body;}
    public void setUserId(int userId){this.userId = userId;}

    public static class Builder {
        private String title;
        private String body;
        private int userId;

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder body(String body) {
            this.body = body;
            return this;
        }

        public Builder userId(int userId) {
            this.userId = userId;
            return this;
        }

        public Post build() {
            return new Post(title, body, userId);
        }
    }
}
