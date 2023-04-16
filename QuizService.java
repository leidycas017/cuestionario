package com.devjunior.cuestionario.service;

import com.devjunior.cuestionario.model.Question;
import com.devjunior.cuestionario.model.QuestionForm;
import com.devjunior.cuestionario.model.Result;
import com.devjunior.cuestionario.repository.QuestionRepo;
import com.devjunior.cuestionario.repository.ResultRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class QuizService {

    @Autowired
    Question question;
    @Autowired
    QuestionForm qForm;
    @Autowired
    QuestionRepo qRepo;
    @Autowired
    Result result;
    @Autowired
    ResultRepo rRepo;



    public QuestionForm getQuestions() {
        List<Question> allQues = qRepo.findAll();
        List<Question> qList = new ArrayList<Question>();

        Random random = new Random();

        for(int i=0; i<6; i++) {
            int rand = random.nextInt(allQues.size());
            qList.add(allQues.get(rand));
            allQues.remove(rand);
        }

        qForm.setQuestions(qList);

        return qForm;
    }

    public String getResult(QuestionForm qForm){
        int conservador = 0;
        int moderado = 0;
        int arriesgado = 0;
        for(Question q : qForm.getQuestions()){
            if(q.getChose() == 1) {
                conservador++;
            }else if(q.getChose() == 2) {
                moderado++;
            }else if(q.getChose() == 3) {
                arriesgado++;
            }else{
                return "NA";
            }
        }
        if(arriesgado >= moderado && arriesgado >= conservador){
            return "Arriesgado";
        }else if(moderado >= conservador && moderado >= arriesgado) {
            return "Moderado";
        }else {
            return "Conservador";
        }
    }

    public void saveScore(Result result) {
        Result saveResult = new Result();
        saveResult.setUsername(result.getUsername());
        saveResult.setPerfil(result.getPerfil());
        rRepo.save(saveResult);
    }

    public List<Result> getTopScore() {
        return rRepo.findAll(Sort.by(Sort.Direction.DESC, "perfil"));
    }
}