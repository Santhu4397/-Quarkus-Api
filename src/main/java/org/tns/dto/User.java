package org.tns.dto;


import io.quarkus.mongodb.panache.PanacheMongoEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import static org.tns.util.ApplicationUtils.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Valid
public class User extends PanacheMongoEntity {

      @Pattern(regexp = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$",
                   message = Email)
      private String email;
      @NotBlank(message="Name" +NOT_BLANK)
      private String name;
      @NotBlank(message="productName"+NOT_BLANK)
      private String productName;
      @Pattern(regexp = "^(((0[13-9]|1[012])[-/]?(0[1-9]|[12][0-9]|30)|(0[13578]|1[02])[-/]?31|02[-/]?(0[1-9]|1[0-9]|2[0-8]))" +
              "[-/]?[0-9]{4}|02[-/]?29[-/]?([0-9]{2}(([2468][048]|[02468][48])|[13579][26])|([13579][26]|[02468][048]|0[0-9]|1[0-6])00))$"
              ,message = "userEndDate"+DATE_FORMAT)
      private String userEndDate;
      @Pattern(regexp = "^(((0[13-9]|1[012])[-/]?(0[1-9]|[12][0-9]|30)|(0[13578]|1[02])[-/]?31|02[-/]?(0[1-9]|1[0-9]|2[0-8]))" +
              "[-/]?[0-9]{4}|02[-/]?29[-/]?([0-9]{2}(([2468][048]|[02468][48])|[13579][26])|([13579][26]|[02468][048]|0[0-9]|1[0-6])00))$"
              ,message = "userStartDate"+DATE_FORMAT)
      private String userStartDate;

}
