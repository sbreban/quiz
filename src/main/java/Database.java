import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Sergiu on 18.01.2016.
 */
public class Database {

  private static Database instance;

  private Map<String, User> users;
  private List<Test> tests;
  private Map<Integer, List<Question>> questions;

  private Database() {
    users = new HashMap<String, User>();
    users.put("alibaba", new User("alibaba", "sesame", "1"));
    tests = new ArrayList<Test>();
    tests.add(new Test(1, "test1", 1));
    questions = new HashMap<Integer, List<Question>>();
    List<Question> test1Questions = new ArrayList<Question>();
    test1Questions.add(new Question(1, "cine?", "a", "b"));
    questions.put(1, test1Questions);
  }

  public static Database getInstance() {
    if (instance == null) {
      instance = new Database();
    }
    return instance;
  }

  public User getUser(String checkName, String checkPassword) {
    User user = users.get(checkName);
    if (!user.getPassword().equals(checkPassword)) {
      return null;
    }
    return user;
  }

  public int getMaxLevel(String tests) {
    int level = 1;
    return level;
  }

  public List<Test> getTests(int maxLevel) {
    return tests;
  }

  public List<Question> getQuestions(int testId) {
    return questions.get(testId);
  }

  public boolean checkAnswer(int questionId, String answer) {
    boolean correct = false;

    List<Question> allQuestions = new ArrayList<Question>();
    for (int testId : questions.keySet()) {
      allQuestions.addAll(questions.get(testId));
    }

    for (Question question : allQuestions) {
      if (question.getId() == questionId && question.getCorrectAnswer().equals(answer)) {
        correct = true;
      }
    }

    return correct;
  }
}
