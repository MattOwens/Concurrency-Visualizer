package us.mattowens.concurrencyvisualizer.datacapture;

import java.util.Map;

public interface JSONSerializable {

	Map<String, Object> collapseToMap();
}
