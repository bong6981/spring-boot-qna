package com.codessquad.qna.web;

import com.codessquad.qna.web.domain.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

public class QuestionPage {

    private final Pageable pageable;
    private int previous;
    private int next;
    private Page<Question> questions;
    private List<Integer> pageNumbers;


    public QuestionPage(Pageable pageable, Page<Question> questionPage) {
        this.pageable = pageable;
        this.questions = questionPage;
        this.previous = questionPage.previousOrFirstPageable().getPageNumber();
        if (questionPage.hasNext()) {
            this.next = questionPage.nextPageable().getPageNumber();
        } else {
            this.next = pageable.getPageNumber();
        }
        this.pageNumbers = createPageNumbers();
    }

    private List<Integer> createPageNumbers() {
        int countNumbersToShow = 3;
        List<Integer> pageNumbers = new ArrayList<>(countNumbersToShow);
        int currentPage = questions.getNumber();
        int totalPages = questions.getTotalPages();
        int startNumber = (currentPage / countNumbersToShow) * countNumbersToShow;
        int endNumber = startNumber + countNumbersToShow - 1;
        for (int i = startNumber; i <= endNumber; i++) {
            pageNumbers.add(i);
            if (i == totalPages - 1) {
                break;
            }
        }
        return pageNumbers;
    }

    public Pageable getPageable() {
        return pageable;
    }

    public int getPrevious() {
        return previous;
    }

    public int getNext() {
        return next;
    }

    public Page<Question> getQuestions() {
        return questions;
    }

    public List<Integer> getPageNumbers() {
        return pageNumbers;
    }
}
