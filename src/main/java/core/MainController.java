package core;

import core.Table;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {
    private static final String template = "Hello, %s!";

    @RequestMapping("/rasp")
    public ResponseEntity<Table> table(
            @RequestParam(value = "email", defaultValue = "") String email,
            @RequestParam(value = "pass", defaultValue = "") String pass,
            @RequestParam(value = "week", defaultValue = "1") String week) {
        return parse(email, pass, week);
    }

    public ResponseEntity<Table> parse(String email, String pass, String week) {
        Parser parser = new Parser();

        parser.get("https://cabs.itut.ru/cabinet/");
        parser.post("https://cabs.itut.ru/cabinet/lib/updatesession.php", "key=parole&value=" + pass);//"key=parole&value=abibos98");
        parser.post("https://cabs.itut.ru/cabinet/lib/updatesession.php", "key=users&value=" + email);//"key=users&value=takexito%40gmail.com");
        parser.get("https://cabs.itut.ru/cabinet/?login=yes");
        if(parser.parse(parser.get("https://cabs.itut.ru/cabinet//project/cabinet/forms/raspisanie.php?week="+week))){
            return  ResponseEntity.ok(parser.getTable());
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    }
}
