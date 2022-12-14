package com.example.historicalquiz;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class MainActivityHistoricalQuiz extends AppCompatActivity {

    private final ArrayList<Question> gameArrayQuestion = new ArrayList<>();
    private TextView pictureHistoricalQuiz;
    private String answerHistoricalQuiz;
    private int rightAnswerCountHistoricalQuiz;
    private TextView[] buttonArrayHistoricalQuiz;
    private Dialog dialogHistoricalQuiz, dialogStart;
    private Handler handlerHistoricalQuiz;
    private Runnable runnableHistoricalQuiz;
    private Question currentQuestion;
    private final ArrayList<String> answers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_historical_quiz);
        pictureHistoricalQuiz = findViewById(R.id.tv_historical_quiz);
        handlerHistoricalQuiz = new Handler();
        runnableHistoricalQuiz = this::setTaskHistoricalQuiz;
        buttonArrayHistoricalQuiz = new TextView[]{findViewById(R.id.answer1), findViewById(R.id.answer2), findViewById(R.id.answer3), findViewById(R.id.answer4)};
        dialogStart = new Dialog(this);
        dialogStart.setContentView(R.layout.dialog_start_historical_quiz);
        dialogStart.show();
    }

    private void startGameHistoricalQuiz() {
        fillArrayHistoricalQuiz();
        rightAnswerCountHistoricalQuiz = 0;
        Collections.shuffle(gameArrayQuestion);
        for (TextView textView : buttonArrayHistoricalQuiz) {
            textView.setVisibility(View.VISIBLE);
        }
        setTaskHistoricalQuiz();
    }

    private void setTaskHistoricalQuiz() {
        if (gameArrayQuestion.size() == 0) {
            endGameHistoricalQuiz();
        } else {
            setVisibility(true);
            setEnabledButtonHistoricalQuiz(true);
            for (TextView tv : buttonArrayHistoricalQuiz) {
                tv.setBackgroundResource(R.drawable.button_historical_quiz);
            }
            Random randomHistoricalQuiz = new Random();
            Collections.shuffle(gameArrayQuestion);
            int rndHistoricalQuiz = randomHistoricalQuiz.nextInt(gameArrayQuestion.size());
            currentQuestion = gameArrayQuestion.get(rndHistoricalQuiz);
            pictureHistoricalQuiz.setText(currentQuestion.getQuestion());
            answers.clear();
            answers.add(currentQuestion.getAnswer());
            answers.add(currentQuestion.getWrongAnswer1());
            answers.add(currentQuestion.getWrongAnswer2());
            if (!currentQuestion.getWrongAnswer3().equals("")) {
                answers.add(currentQuestion.getWrongAnswer3());
            }else {
                setVisibility(false);
            }
            Collections.shuffle(answers);
            for (int i = 0; i < answers.size(); i++) {
                buttonArrayHistoricalQuiz[i].setText(answers.get(i));
            }

            answerHistoricalQuiz = currentQuestion.getAnswer();
        }
    }

    private void endGameHistoricalQuiz() {
        gameArrayQuestion.clear();
        setEnabledButtonHistoricalQuiz(false);
        dialogHistoricalQuiz = new Dialog(this);
        dialogHistoricalQuiz.setContentView(R.layout.dialog_end_historical_quiz);
        TextView scoreHistoricalQuiz = dialogHistoricalQuiz.findViewById(R.id.tv_score_historical_quiz);
        scoreHistoricalQuiz.setText(String.format(getString(R.string.score_historical_quiz), rightAnswerCountHistoricalQuiz));
        dialogHistoricalQuiz.show();
    }


    private void setEnabledButtonHistoricalQuiz(boolean b) {
        for (TextView tv : buttonArrayHistoricalQuiz) {
            tv.setEnabled(b);
        }
    }

    public void onClickRestartHistoricalQuiz(View view) {
        dialogHistoricalQuiz.dismiss();
        startGameHistoricalQuiz();
    }

    public void onClickExitHistoricalQuiz(View view) {
        dialogHistoricalQuiz.dismiss();
        finish();
    }

    public void onClickAnswerHistoricalQuiz(View view) {
        TextView tv = (TextView) view;
        setEnabledButtonHistoricalQuiz(false);
        if (tv.getText().toString().equals(answerHistoricalQuiz)) {
            tv.setBackgroundResource(R.drawable.button_true_green_historical_quiz);
            rightAnswerCountHistoricalQuiz++;
        } else {
            for (TextView textView : buttonArrayHistoricalQuiz) {
                if (textView.getText().toString().equals(answerHistoricalQuiz)) {
                    textView.setBackgroundResource(R.drawable.button_true_green_historical_quiz);
                }
            }
            tv.setBackgroundResource(R.drawable.button_false_red_historical_quiz);
        }
        gameArrayQuestion.remove(currentQuestion);
        currentQuestion = null;
        handlerHistoricalQuiz.postDelayed(runnableHistoricalQuiz, 500);
    }

    private void fillArrayHistoricalQuiz() {
        gameArrayQuestion.add(new Question("Пост наркома иностранных дел СССР накануне и во время Великой Отечественной войны занимал:", "М. Молотов", "М. Литвинов", "М. Каганович", ""));
        gameArrayQuestion.add(new Question("В сентябре 1939 г. к СССР были присоединены:", "Западная Украина", "Бессарабия", "Варшавское воеводство", ""));
        gameArrayQuestion.add(new Question("Прорывом «линии Маннергейма» руководил:", "С. К. Тимошенко", "Г. К. Жуков", "К. Е. Ворошилов", ""));
        gameArrayQuestion.add(new Question("В каком году главами государств СНГ было подписано Соглашение о создании Межгосударственного экологического совета стран СНГ?", "1992", "1995", "1993", "1994"));
        gameArrayQuestion.add(new Question(" Какое из государств СНГ первым подписало с Европейским Союзом Соглашение о партнёрстве и сотрудничестве?", "Россия", "Украина", "Казахстан", "Узбекистан"));
        gameArrayQuestion.add(new Question("По приказу какого правителя отправился Флорио Беневени в Среднюю Азию?", "Петра I", "Ивана IV", "Александра I", "Екатерины II"));
        gameArrayQuestion.add(new Question("В каком году в результате подписания советско-японской декларации было оформлено формальное прекращение состояния войны между бывшим СССР и Японией?", "1956", "1958", "1946", "1948"));
        gameArrayQuestion.add(new Question("Кем были построены первые каменные храмы на Руси?", "византийскими архитекторами", "Аристотелем Фиорованти", "итальянскими зодчими", "Феофаном Греком"));
        gameArrayQuestion.add(new Question("Определите русского путешественника, руководившего кругосветной экспедицией и открывшего 12 островов", "Ф.П.Литке", "Ф.Ф.Беллинсгаузен", "Г.И.Невельской", "И.В.Крузенштерн"));
        gameArrayQuestion.add(new Question("Кто сыграл выдающуюся роль в строительстве укреплений Севастополя?", "Э.И.Тотлебен", "В.А.Корнилов", "П.Конека", "В.И.Истомин"));
        gameArrayQuestion.add(new Question("Какое событие произошло в России спустя год после того, как Абдуллахан официально был признан верховным правителем государства Шейбанидов?", "основан Архангельск", "реформы Ивана IV", "началось восстание Хлопка", "завоевано Казанское ханство"));
        gameArrayQuestion.add(new Question("Какое посольство представляло особый интерес в истории русско-бухарских торговых и дипломатических отношений во второй половине XVIII в.?", "И.Максютова", "К.Рахматбекова", "А.Дженкинсона", "И.Хохлова"));
        gameArrayQuestion.add(new Question("В истории России \"золотым веком\" дворяне называли период правления ", "Екатерины II", "Петра I", "Александра I", "Павла"));
        gameArrayQuestion.add(new Question("С какого времени глава русской церкви стал называться патриархом?", "с 1589 г.", "с 1721 г.", "с 1649 г.", "с 1613 г."));
        gameArrayQuestion.add(new Question("Сколько республик подписали в 1991 г. в Алма-Ате протокол соглашения об образовании СНГ?", "9", "11", "15", "8"));
        gameArrayQuestion.add(new Question("Одной из главных задач России в конце XVII в. был", "выход к Черному морю", "выход к Тихому океану", "завоевание Сибири", "разгром Швеции и Германии"));
        gameArrayQuestion.add(new Question("Столица Крымского ханства в XV в.", "Бахчисарай", "Кафа", "Азов", "Керчь"));
        gameArrayQuestion.add(new Question("В каком году были установлены дипломатические отношения между США и бывшим СССР?", "1933", "1930", "1928", "1935"));
        gameArrayQuestion.add(new Question("На какой территории расселились печенеги в конце IХ-Х вв.?", "в низовьях Днепра", "в верховьях Дона", "в степях Поволжья", "на средней Волге"));
        gameArrayQuestion.add(new Question("Столица Золотой Орды в первой половине XIV века во время правления Узбекхана?", "Сарай Берке", "Сувар", "Сарай Бату", "Саркел"));
    }

    public void onClickStartHistoricalQuiz(View view) {
        dialogStart.dismiss();
        startGameHistoricalQuiz();
    }

    private void setVisibility(boolean b) {
        if (b) {
            buttonArrayHistoricalQuiz[3].setVisibility(View.VISIBLE);
        } else {
                buttonArrayHistoricalQuiz[3].setVisibility(View.INVISIBLE);
        }
    }
}