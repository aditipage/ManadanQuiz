/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BeanClasses;

/**
 *
 * @author Dumbledore
 */
public class DetailedResult {
    int result_id, question_id;
    String marked_answer;

    public DetailedResult(int result_id, int question_id, String marked_answer) {
        this.result_id = result_id;
        this.question_id = question_id;
        this.marked_answer = marked_answer;
    }

    public int getResult_id() {
        return result_id;
    }

    public void setResult_id(int result_id) {
        this.result_id = result_id;
    }

    public int getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(int question_id) {
        this.question_id = question_id;
    }

    public String getMarked_answer() {
        return marked_answer;
    }

    public void setMarked_answer(String marked_answer) {
        this.marked_answer = marked_answer;
    }
    
}
