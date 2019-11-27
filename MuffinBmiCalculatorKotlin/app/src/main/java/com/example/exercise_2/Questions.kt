package com.example.exercise_2

object Questions {

    private val questions = arrayOf("Ile kalorii powinna jesc dziennie przecietna osoba?", "Ktore BMI pokazuje prawidlowa wage?", "Ile dziennie nalezy jesc posilkow?", "Co w pierwszej kolejności powinienes ograniczyć przy zdrowej diecie?", "O której jeśc śniadanie ?")

    private val choices = arrayOf(arrayOf("1000", "1500", "3000", "2000"), arrayOf("15", "18", "22", "28"), arrayOf("3", "2", "5", "1"), arrayOf("Węglowodany(Polisacharydy)", "Białko", "Cukry(Mono+oligosacharydy)", "Tłuszcze"), arrayOf("Do godziny po obudzeniu", "Dwie godziny po obudzeniu", "Trzy godziny po obudzeniu", "Nie jeść śniadania"))

    private val correctAnswer = arrayOf("2000", "22", "5", "Cukry(Mono+oligosacharydy)", "Do godziny po obudzeniu")

    internal fun getQuestion(questionNumber: Int): String {
        return questions[questionNumber]
    }

    internal fun getChoice(questionNumber: Int, choiceNumber: Int): String {
        return choices[questionNumber][choiceNumber]
    }

    internal fun getCorrectAnwer(questionNumber: Int): String {
        return correctAnswer[questionNumber]
    }

}
