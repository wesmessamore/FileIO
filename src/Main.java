// Program created by Wes Messamore on February 9, 2017
// short customer satisfaction survey that save answers to a JSON object in a file
// If the JSON parsing fails, give a friendly error rather than crashing the program.

import jodd.json.JsonParser;
import jodd.json.JsonSerializer;

import java.io.File;
import java.io.FileWriter;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception {


        while (true) {

            //GREETING AND PROMPT
            System.out.println("==SURVEY SOLUTIONS==");
            System.out.println("SATISFACTION SURVEY: Jurassic Park");
            System.out.println("Would you like to start a new survey or find an old survey?");
            System.out.println("Enter New or Find...");

            Scanner scanner = new Scanner(System.in);

            String newOrFind = scanner.nextLine();


            switch (newOrFind) {

                //IF USER PICKS NEW, GIVE SURVEY AND SAVE ANSWERS

                case "New":

                    //5 question survey about a service
                   Survey survey = shortSurvey();

                    //save survey answers into a JSON object in a file
                    saveAnswers(survey);

                    break;

                // IF USER PICKS FIND...

                case "Find":

                    // ...pull up old survey and display answers...
                    // ...then runs changeAnswersOrExit()...

                    readFileandDisplayAnswers();

                    break;

                default:

                    System.out.println("Not a valid answer...");
            }


        }

    }

    private static void readFileandDisplayAnswers() throws Exception {
        // read json
        try {
        File surveyFile = new File("survey.json");
        Scanner scanner = new Scanner(surveyFile);
        scanner.useDelimiter("\\Z");
        String contents = scanner.next();
        scanner.close();
        JsonParser parser = new JsonParser();
        Survey survey2 = parser.parse(contents, Survey.class);

        System.out.println("Name:");
        System.out.println("===>>> [" + survey2.getName() + "]");
        System.out.println("1. Was this your first visit to Jurassic Park?");
        System.out.println("===>>> [" + survey2.getFirstVisitOrNo() + "]");
        System.out.println("2. Was your interest in Jurassic Park scientific or recreational?");
        System.out.println("===>>> [" + survey2.getScientificOrRecreational() + "]");
        System.out.println("3. Would you recommend a vacation to Jurassic Park to a friend?");
        System.out.println("===>>> [" + survey2.getRecommendFriendOrNo() + "]");
        System.out.println("4. What was your favoite exhibit?");
        System.out.println("===>>> [" + survey2.getFavoriteExhibit() + "]");
        System.out.println("5. How would you rate your satisfaction with Jurassic Park overall?");
        System.out.println("===>>> [" + survey2.getSatisfactionRating() + "]\n");

            changeAnswersOrExit();

        }
        catch (NoSuchElementException e){
            System.out.println("No survey currently saved.");
        }

    }

    private static void saveAnswers(Survey survey) throws Exception {

        File surveyFile = new File("survey.json");

        // write json
        JsonSerializer serializer = new JsonSerializer();
        String json = serializer.serialize(survey);
        FileWriter fw = new FileWriter(surveyFile);
        fw.write(json);
        fw.close();
    }

    private static Survey shortSurvey() {
        Scanner scanner = new Scanner(System.in);

        Survey survey = new Survey();

        System.out.println("Please enter your first and last name separated by a space");
        survey.name = scanner.nextLine();
        System.out.println("1. Was this your first visit to Jurassic Park?");
        survey.firstVisitOrNo = scanner.nextLine();
        System.out.println("2. Was your interest in Jurassic Park scientific or recreational?");
        survey.scientificOrRecreational = scanner.nextLine();
        System.out.println("3. Would you recommend a vacation to Jurassic Park to a friend?");
        survey.recommendFriendOrNo = scanner.nextLine();
        System.out.println("4. What was your favoite exhibit? Please choose one.");
        System.out.println("T-Rex, Velicoraptor, Dilophosaurus, Brachiosaurus, Stegosaurus, Triceratops, or Other");
        survey.favoriteExhibit = scanner.nextLine();
        System.out.println("5. How would you rate your satisfaction with Jurassic Park overall?");
        System.out.println("Please enter: Poor, Fair, Good, Very Good, Excellent");
        survey.satisfactionRating = scanner.nextLine();

        System.out.println("Survey saved!");
        System.out.println("Thank you for using Survey Solutions!");

        return survey;

    }


    private static void changeAnswersOrExit() throws Exception {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Would you like to change any of your answers or exit survey?");
        System.out.println("Enter Change or Exit...");

        String changeOrExit = scanner.nextLine();

        if (changeOrExit.equals("Change")) {

            //5 question survey about a service
            Survey survey = shortSurvey();

            //save survey answers into a JSON object in a file
            saveAnswers(survey);

        } else if (changeOrExit.equals("Exit")) {
            System.out.println("Thank you for using Survey Solutions!");
        }

    }

}