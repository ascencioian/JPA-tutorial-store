package com.example.springbootdata.jpa.myservices;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.springbootdata.jpa.model.Tutorial;
import com.example.springbootdata.jpa.repository.TutorialRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TutorialServices {

   @Autowired
   private TutorialRepository tutorialRepository;

   public List<Tutorial> getAllTutorials() {

       List<Tutorial> tutorials = new ArrayList<Tutorial>();
       tutorialRepository.findAll().forEach(tutorials::add);
       return tutorials;
   }


   public Optional<Tutorial> getTutorialById(Long id) {
            return  tutorialRepository.findById(id);
   }

   public void addTutorial(Tutorial tutorial) {
       tutorialRepository.save(tutorial);
   }

   public void updateTutorial(long id, Tutorial tutorial) {
       Optional<Tutorial> tutorialData = tutorialRepository.findById(id);

       if (tutorialData.isPresent()) {
           Tutorial _tutorial = tutorialData.get();
           _tutorial.setTitle(tutorial.getTitle());
           _tutorial.setDescription(tutorial.getDescription());
           _tutorial.setPublished(tutorial.isPublished());
           tutorialRepository.save(_tutorial);
       }
   }

   public void deleteTutorial(long id) {
       tutorialRepository.deleteById(id);
   }

   public void deleteAllTutorials() {
       tutorialRepository.deleteAll();
   }

   public ResponseEntity<List<Tutorial>> findByPublished() {
       try {
           List<Tutorial> tutorials = tutorialRepository.findByPublished(true);

           if (tutorials.isEmpty()) {
               return new ResponseEntity<>(HttpStatus.NO_CONTENT);
           }
           return new ResponseEntity<>(tutorials, HttpStatus.OK);
       } catch (Exception e) {
           return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
       }
   }
}


