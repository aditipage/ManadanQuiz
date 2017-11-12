/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BeanClasses;

import java.sql.Timestamp;

/**
 *
 * @author Dumbledore
 */
public class Quiz {
    int result_id, number_of_questions, level, duration, score;
    String topic_id, user_name;
    Timestamp time;

    public Quiz(int result_id, int number_of_questions, int level, int duration, int score, String topic_id, String user_name, Timestamp time) {
        this.result_id = result_id;
        this.number_of_questions = number_of_questions;
        this.level = level;
        this.duration = duration;
        this.score = score;
        this.topic_id = topic_id;
        this.user_name = user_name;
    }

    public int getResult_id() {
        return result_id;
    }

    public void setResult_id(int result_id) {
        this.result_id = result_id;
    }

    public int getNumber_of_questions() {
        return number_of_questions;
    }

    public void setNumber_of_questions(int number_of_questions) {
        this.number_of_questions = number_of_questions;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getTopic_id() {
        return topic_id;
    }

    public void setTopic_id(String topic_id) {
        this.topic_id = topic_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }
    
}
