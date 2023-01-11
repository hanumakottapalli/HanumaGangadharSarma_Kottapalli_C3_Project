import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class RestaurantTest {
	Restaurant restaurant;
	List<String> selectedItems = new ArrayList<String>();

	// REFACTOR ALL THE REPEATED LINES OF CODE
	public void addRestaurant() {
		LocalTime openingTime = LocalTime.parse("10:30:00");
		LocalTime closingTime = LocalTime.parse("22:00:00");
		restaurant = new Restaurant("Amelie's cafe", "Chennai", openingTime, closingTime);
		restaurant.addToMenu("Sweet corn soup", 119);
		restaurant.addToMenu("Vegetable lasagne", 269);

	}

	// >>>>>>>>>>>>>>>>>>>>>>>>>OPEN/CLOSED<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
	// -------FOR THE 2 TESTS BELOW, YOU MAY USE THE CONCEPT OF MOCKING, IF YOU RUN
	// INTO ANY TROUBLE
	@Test
	public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time() {

		LocalTime openingTime = LocalTime.parse("10:30:00");
		LocalTime closingTime = LocalTime.now().plusMinutes(10);
		restaurant = new Restaurant("Amelie's cafe", "Chennai", openingTime, closingTime);
		assertTrue(restaurant.isRestaurantOpen());
	}

	@Test
	public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time() {
		// WRITE UNIT TEST CASE HERE
		LocalTime openingTime = LocalTime.parse("10:30:00");
		LocalTime closingTime = LocalTime.now().minusMinutes(10);
		restaurant = new Restaurant("Amelie's cafe", "Chennai", openingTime, closingTime);
		assertFalse(restaurant.isRestaurantOpen());
	}

	// <<<<<<<<<<<<<<<<<<<<<<<<<OPEN/CLOSED>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

	// >>>>>>>>>>>>>>>>>>>>>>>>>>>MENU<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
	@Test
	public void adding_item_to_menu_should_increase_menu_size_by_1() {
		addRestaurant();
		int initialMenuSize = restaurant.getMenu().size();
		restaurant.addToMenu("Sizzling brownie", 319);
		assertEquals(initialMenuSize + 1, restaurant.getMenu().size());
	}

	@Test
	public void removing_item_from_menu_should_decrease_menu_size_by_1() throws itemNotFoundException {
		addRestaurant();
		int initialMenuSize = restaurant.getMenu().size();
		restaurant.removeFromMenu("Vegetable lasagne");
		assertEquals(initialMenuSize - 1, restaurant.getMenu().size());
	}

	@Test
	public void removing_item_that_does_not_exist_should_throw_exception() {
		addRestaurant();
		assertThrows(itemNotFoundException.class, () -> restaurant.removeFromMenu("French fries"));
	}

	// <<<<<<<<<<<<<<<<<<<<<<<MENU>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

	// <<<<<<<<<<<<<<<<<<<<<<<<<<<Order Total>>>>>>>>>>>>>>>>>>>>>>

	@Test
	public void order_Sweet_Corn_Soup_verify_Total() {
		addRestaurant();
		selectedItems.add("Sweet corn soup");
		int price = restaurant.getOrderTotal(selectedItems);
		assertEquals(price, 119);
	}

	@Test
	public void order_Vegetable_lasagne_verify_Total() {
		addRestaurant();
		selectedItems.add("Vegetable lasagne");
		int price = restaurant.getOrderTotal(selectedItems);
		assertEquals(price, 269);
	}

	@Test
	public void order_all_items_verify_Total() {
		addRestaurant();
		selectedItems.add("Sweet corn soup");
		selectedItems.add("Vegetable lasagne");
		int price = restaurant.getOrderTotal(selectedItems);
		assertEquals(price, 388);
	}

	@Test
	public void remove_One_item_verify_Total() throws itemNotFoundException {
		addRestaurant();
		restaurant.addToMenu("Sizzling brownie", 319);
		selectedItems.add("Sweet corn soup");
		selectedItems.add("Vegetable lasagne");
		selectedItems.add("Sizzling brownie");
		int beforeRemovingPrice = restaurant.getOrderTotal(selectedItems);
		assertEquals(beforeRemovingPrice, 707);
		restaurant.removeFromMenu("Sizzling brownie");
		selectedItems.remove("Sizzling brownie");
		int afterRemovingPrice = restaurant.getOrderTotal(selectedItems);
		assertEquals(afterRemovingPrice, 388);

	}

}