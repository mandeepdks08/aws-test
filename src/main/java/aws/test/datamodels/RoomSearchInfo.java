package aws.test.datamodels;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoomSearchInfo {
	private int numberOfAdults;
	private Integer numberOfChild;
	private List<Integer> childAge;
}