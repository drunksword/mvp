package site.shitao.spring;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.context.annotation.ComponentScan;

/**
 * Author: tao.shi@xxx.com
 * Created: 7/20
 */
@Data @AllArgsConstructor
@ComponentScan("site.shitao")
public class MethodLog {
    private String componentScan;
    private boolean isImportFootball;
}
