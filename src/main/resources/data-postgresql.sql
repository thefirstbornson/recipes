INSERT INTO public.tblcourse (course_id, course) VALUES (1, 'суп');
INSERT INTO public.tblcourse (course_id, course) VALUES (2, 'основное блюдо');
INSERT INTO public.tblcourse (course_id, course) VALUES (3, 'салат');
INSERT INTO public.tblcourse (course_id, course) VALUES (4, 'гарнир');
INSERT INTO public.tblcourse (course_id, course) VALUES (5, 'десерт');
INSERT INTO public.tblcourse (course_id, course) VALUES (6, 'напиток');


INSERT INTO public.tblcuisine (cuisine_id, cuisine) VALUES (1, 'европейская');
INSERT INTO public.tblcuisine (cuisine_id, cuisine) VALUES (2, 'средиземноморская');
INSERT INTO public.tblcuisine (cuisine_id, cuisine) VALUES (3, 'мексиканская');
INSERT INTO public.tblcuisine (cuisine_id, cuisine) VALUES (4, 'русская');

INSERT INTO public.tblfoodcategory (food_category_id, food_category) VALUES (1, 'молочные продукты');
INSERT INTO public.tblfoodcategory (food_category_id, food_category) VALUES (2, 'овощи');
INSERT INTO public.tblfoodcategory (food_category_id, food_category) VALUES (3, 'злаковые');
INSERT INTO public.tblfoodcategory (food_category_id, food_category) VALUES (4, 'фрукты');
INSERT INTO public.tblfoodcategory (food_category_id, food_category) VALUES (5, 'птица');
INSERT INTO public.tblfoodcategory (food_category_id, food_category) VALUES (6, 'мясо');

INSERT INTO public.tblingredient (ingredient_id, name) VALUES (1, 'рис');
INSERT INTO public.tblingredient (ingredient_id, name) VALUES (2, 'лук');
INSERT INTO public.tblingredient (ingredient_id, name) VALUES (3, 'морковь');
INSERT INTO public.tblingredient (ingredient_id, name) VALUES (4, 'консервированный тунец');

INSERT INTO public.tbllevel (level_id) VALUES (1);
INSERT INTO public.tbllevel (level_id) VALUES (2);
INSERT INTO public.tbllevel (level_id) VALUES (3);

INSERT INTO public.tblmeal (meal_id, meal) VALUES (1, 'завтрак');
INSERT INTO public.tblmeal (meal_id, meal) VALUES (2, 'обед');
INSERT INTO public.tblmeal (meal_id, meal) VALUES (3, 'ужин');

INSERT INTO public.tblmeasurement (measurement_id, name) VALUES (1, 'гр.');
INSERT INTO public.tblmeasurement (measurement_id, name) VALUES (2, 'мл.');

INSERT INTO public.tblnutritionalinformation (nutrition_information_id, name) VALUES (1, 'жир');
INSERT INTO public.tblnutritionalinformation (nutrition_information_id, name) VALUES (2, 'белок');
INSERT INTO public.tblnutritionalinformation (nutrition_information_id, name) VALUES (3, 'углевод');

-- INSERT INTO public.tblrecipe (recipe_id, cooktime, cuisine_id, description, imagepath, instructions, level_id, name, rating) VALUES (1, 20, 1, 'быстрое и вкусное блюдо ', '', '1. Сварить рис, 2. Обжарить лук, морковь, консервированный тунец, 3. добавить рис к зажарке', 1, 'рис с тунцом', 1);

INSERT INTO public.tblingredientnutritionaninformation (rni_id, amount, ingredient_id, ni_id) VALUES (1, 1, 1, 2);
INSERT INTO public.tblingredientnutritionaninformation (rni_id, amount, ingredient_id, ni_id) VALUES (2, 1, 1, 3);
INSERT INTO public.tblingredientnutritionaninformation (rni_id, amount, ingredient_id, ni_id) VALUES (3, 1, 1, 1);
INSERT INTO public.tblingredientnutritionaninformation (rni_id, amount, ingredient_id, ni_id) VALUES (4, 1, 2, 3);
INSERT INTO public.tblingredientnutritionaninformation (rni_id, amount, ingredient_id, ni_id) VALUES (5, 1, 2, 1);
INSERT INTO public.tblingredientnutritionaninformation (rni_id, amount, ingredient_id, ni_id) VALUES (6, 1, 2, 2);
INSERT INTO public.tblingredientnutritionaninformation (rni_id, amount, ingredient_id, ni_id) VALUES (7, 1, 3, 1);
INSERT INTO public.tblingredientnutritionaninformation (rni_id, amount, ingredient_id, ni_id) VALUES (8, 1, 3, 3);
INSERT INTO public.tblingredientnutritionaninformation (rni_id, amount, ingredient_id, ni_id) VALUES (9, 1, 3, 2);
INSERT INTO public.tblingredientnutritionaninformation (rni_id, amount, ingredient_id, ni_id) VALUES (10, 1, 4, 2);
INSERT INTO public.tblingredientnutritionaninformation (rni_id, amount, ingredient_id, ni_id) VALUES (11, 1, 4, 3);
INSERT INTO public.tblingredientnutritionaninformation (rni_id, amount, ingredient_id, ni_id) VALUES (12, 1, 4, 1);


-- INSERT INTO public.tblrecipecourse (recipe_id, course_id) VALUES (1, 1);
--
-- INSERT INTO public.tblrecipefoodcategory (recipe_id, food_category_id) VALUES (1, 2);
-- INSERT INTO public.tblrecipefoodcategory (recipe_id, food_category_id) VALUES (1, 1);
--
-- INSERT INTO public.tblrecipeingredient (recipe_ingredient_id, amount, ingredient_id, measurement_id, recipe_id) VALUES (1, 1,1, 1, 1);
-- INSERT INTO public.tblrecipeingredient (recipe_ingredient_id, amount, ingredient_id, measurement_id, recipe_id) VALUES (2, 1, 2, 1, 1);
--
--
-- INSERT INTO public.tblrecipemeals (recipe_id, meal_id) VALUES (1, 1);