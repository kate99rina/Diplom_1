package praktikum.burger;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import praktikum.Bun;
import praktikum.Burger;
import praktikum.Ingredient;
import praktikum.IngredientType;
import praktikum.util.BunsInfo;
import praktikum.util.Filling;
import praktikum.util.Sauce;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class BurgerTest {
    @Mock
    private Bun bun;
    @Mock
    private Ingredient ingredient1;
    @Mock
    private Ingredient ingredient2;


    @Test
    public void checkGetPrice() {
        float ingredientPrice1 = 3.1F;
        float ingredientPrice2 = 6.4F;
        float bunPrice = 2.6F;
        Burger burger = new Burger();

        burger.setBuns(bun);
        burger.addIngredient(ingredient1);
        burger.addIngredient(ingredient2);
        float expectedBurgerPrice = bunPrice * 2 + ingredientPrice1 + ingredientPrice2;

        Mockito.when(bun.getPrice()).thenReturn(bunPrice);
        Mockito.when(ingredient1.getPrice()).thenReturn(ingredientPrice1);
        Mockito.when(ingredient2.getPrice()).thenReturn(ingredientPrice2);
        float actualBurgerPrice = burger.getPrice();

        assertEquals("Invalid price", expectedBurgerPrice, actualBurgerPrice, 0.0F);
    }

    @Test
    public void checkRemoveIngredient() {
        float ingredientPrice1 = 3.5F;
        float bunPrice = 2.5F;
        Burger burger = new Burger();

        burger.setBuns(bun);
        burger.addIngredient(ingredient1);
        burger.addIngredient(ingredient2);
        burger.removeIngredient(1);
        float expectedBurgerPrice = bunPrice * 2 + ingredientPrice1;

        Mockito.when(bun.getPrice()).thenReturn(bunPrice);
        Mockito.when(ingredient1.getPrice()).thenReturn(ingredientPrice1);
        float actualBurgerPrice = burger.getPrice();

        assertEquals("Invalid price", expectedBurgerPrice, actualBurgerPrice, 0.0F);
    }

    @Test
    public void checkMoveIngredient() {
        float ingredientPrice1 = 1.0F;
        float ingredientPrice2 = 9.0F;
        float bunPrice = 2.5F;
        Burger burger = new Burger();

        burger.setBuns(bun);
        burger.addIngredient(ingredient1);
        burger.addIngredient(ingredient2);
        burger.moveIngredient(1, 0);
        float expectedBurgerPrice = bunPrice * 2 + ingredientPrice1 + ingredientPrice2;

        Mockito.when(bun.getName()).thenReturn(BunsInfo.BLACK.getName());
        Mockito.when(bun.getPrice()).thenReturn(bunPrice);

        Mockito.when(ingredient1.getName()).thenReturn(Sauce.CHILI.getName());
        Mockito.when(ingredient1.getType()).thenReturn(IngredientType.SAUCE);
        Mockito.when(ingredient1.getPrice()).thenReturn(ingredientPrice1);

        Mockito.when(ingredient2.getName()).thenReturn(Filling.CUTLET.getName());
        Mockito.when(ingredient2.getType()).thenReturn(IngredientType.FILLING);
        Mockito.when(ingredient2.getPrice()).thenReturn(ingredientPrice2);

        assertEquals("Invalid receipt",
                formatReceipt(bun, List.of(ingredient2, ingredient1), expectedBurgerPrice),
                burger.getReceipt());
    }


    @Test
    public void checkGetReceipt() {
        float ingredientPrice1 = 3.0F;
        float ingredientPrice2 = 6.0F;
        float bunPrice = 2.0F;
        float burgerPrice = bunPrice * 2 + ingredientPrice1 + ingredientPrice2;
        Burger burger = new Burger();

        burger.setBuns(bun);
        burger.addIngredient(ingredient1);
        burger.addIngredient(ingredient2);

        Mockito.when(bun.getPrice()).thenReturn(bunPrice);
        Mockito.when(bun.getName()).thenReturn("Пита");

        Mockito.when(ingredient1.getPrice()).thenReturn(ingredientPrice1);
        Mockito.when(ingredient1.getName()).thenReturn("Салат");
        Mockito.when(ingredient1.getType()).thenReturn(IngredientType.FILLING);

        Mockito.when(ingredient2.getPrice()).thenReturn(ingredientPrice2);
        Mockito.when(ingredient2.getName()).thenReturn("Котлета");
        Mockito.when(ingredient2.getType()).thenReturn(IngredientType.FILLING);
        assertEquals("Invalid receipt",
                formatReceipt(bun, List.of(ingredient1, ingredient2), burgerPrice),
                burger.getReceipt());
    }

    private String formatReceipt(Bun bun, List<Ingredient> ingredients, float burgerPrice) {
        StringBuilder receipt = new StringBuilder(String.format("(==== %s ====)%n", bun.getName()));

        for (Ingredient ingredient : ingredients) {
            receipt.append(String.format("= %s %s =%n", ingredient.getType().toString().toLowerCase(),
                    ingredient.getName()));
        }

        receipt.append(String.format("(==== %s ====)%n", bun.getName()));
        receipt.append(String.format("%nPrice: %f%n", burgerPrice));

        return receipt.toString();
    }
}
