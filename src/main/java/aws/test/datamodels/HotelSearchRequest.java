package aws.test.datamodels;

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
public class HotelSearchRequest {
	private HotelSearchQuery searchQuery;
	private Boolean sync;
}
