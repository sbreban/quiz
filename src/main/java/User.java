/**
 * Created by sbreban on 12/14/16.
 */
public class User {
  private String name;
  private String password;
  private String tests;

  public User(String name, String password, String tests) {
    this.name = name;
    this.password = password;
    this.tests = tests;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getTests() {
    return tests;
  }

  public void setTests(String tests) {
    this.tests = tests;
  }
}
