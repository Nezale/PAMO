package com.example.exercise_2;

public class Questions {

    private static String questions[] = {
            "Ile kalorii powinna jesc dziennie przecietna osoba?",
            "Ktore BMI pokazuje prawidlowa wage?",
            "Ile dziennie nalezy jesc posilkow?",
            "Co w pierwszej kolejności powinienes ograniczyć przy zdrowej diecie?",
            "O której jeśc śniadanie ?"

    };

    private static String choices[][] = {
            {"1000", "1500", "3000", "2000"},
            {"15", "18", "22", "28"},
            {"3", "2", "5", "1"},
            {"Węglowodany(Polisacharydy)", "Białko", "Cukry(Mono+oligosacharydy)", "Tłuszcze"},
            {"Do godziny po obudzeniu", "Dwie godziny po obudzeniu", "Trzy godziny po obudzeniu", "Nie jeść śniadania"}
    };

    private static String correctAnswer[] = {"2000", "22", "5", "Cukry(Mono+oligosacharydy)", "Do godziny po obudzeniu"};

    static String getQuestion(int questionNumber) {
        return questions[questionNumber];
    }

    static String getChoice(int questionNumber, int choiceNumber) {
        return choices[questionNumber][choiceNumber];
    }

    static String getCorrectAnwer(int questionNumber) {
        return correctAnswer[questionNumber];
    }

}
