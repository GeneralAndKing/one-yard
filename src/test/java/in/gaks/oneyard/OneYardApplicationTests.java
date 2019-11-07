package in.gaks.oneyard;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@SpringBootTest
@ExtendWith(SpringExtension.class)
class OneYardApplicationTests {

  private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);

  @Test
  void contextLoads() {
    System.out.println(passwordEncoder.encode("123456789"));
  }

}
