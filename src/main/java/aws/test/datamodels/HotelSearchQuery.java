package aws.test.datamodels;

import java.time.LocalDate;
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
public class HotelSearchQuery {
	private String searchId;
	private LocalDate checkinDate;
	private LocalDate checkoutDate;
	private List<RoomSearchInfo> roomInfo;
//	private HotelSearchCriteria searchCriteria;
//	private HotelSearchPreferences searchPreferences;
}