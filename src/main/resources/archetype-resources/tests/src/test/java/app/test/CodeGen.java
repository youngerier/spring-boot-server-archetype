#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.app.test;

import ${package}.biz.user.dal.entity.User;
import io.github.youngerier.generator.GeneratorConfig;
import io.github.youngerier.generator.GeneratorEngine;

import java.util.List;

public class CodeGen {
    public static void main(String[] args) {


        // 2. 构建 GeneratorConfig
        GeneratorConfig config = GeneratorConfig.builder()
                .outputBaseDir("${artifactId}/target/generated-sources")
                .pojoClasses(List.of(
                        User.class
                ))
                .build();

        // 3. 创建并执行引擎
        GeneratorEngine engine = new GeneratorEngine(config);
        engine.execute();
    }
}
