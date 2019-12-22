INSERT INTO tblcourse (course_id, course) VALUES (1, 'суп');
INSERT INTO tblcourse (course_id, course) VALUES (2, 'основное блюдо');
INSERT INTO tblcourse (course_id, course) VALUES (3, 'салат');
INSERT INTO tblcourse (course_id, course) VALUES (4, 'гарнир');
INSERT INTO tblcourse (course_id, course) VALUES (5, 'десерт');
INSERT INTO tblcourse (course_id, course) VALUES (6, 'напиток');
-- ALTER SEQUENCE tblcourse_course_id_seq RESTART WITH 7;

INSERT INTO tblcuisine (cuisine_id, cuisine) VALUES (1, 'европейская');
INSERT INTO tblcuisine (cuisine_id, cuisine) VALUES (2, 'средиземноморская');
INSERT INTO tblcuisine (cuisine_id, cuisine) VALUES (3, 'мексиканская');
INSERT INTO tblcuisine (cuisine_id, cuisine) VALUES (4, 'русская');
-- ALTER SEQUENCE tblcuisine_cuisine_id_seq RESTART WITH 5;

INSERT INTO tblfoodcategory (food_category_id, food_category) VALUES (1, 'молочные продукты');
INSERT INTO tblfoodcategory (food_category_id, food_category) VALUES (2, 'овощи');
INSERT INTO tblfoodcategory (food_category_id, food_category) VALUES (3, 'злаковые');
INSERT INTO tblfoodcategory (food_category_id, food_category) VALUES (4, 'фрукты');
INSERT INTO tblfoodcategory (food_category_id, food_category) VALUES (5, 'птица');
INSERT INTO tblfoodcategory (food_category_id, food_category) VALUES (6, 'мясо');
-- ALTER SEQUENCE tblfoodcategory_food_category_id_seq RESTART WITH 7;

INSERT INTO tblingredient (ingredient_id, name) VALUES (1, 'рис');
INSERT INTO tblingredient (ingredient_id, name) VALUES (2, 'лук');
INSERT INTO tblingredient (ingredient_id, name) VALUES (3, 'морковь');
INSERT INTO tblingredient (ingredient_id, name) VALUES (4, 'консервированный тунец');
-- ALTER SEQUENCE tblingredient_ingredient_id_seq RESTART WITH 5;

INSERT INTO tbllevel (level_id) VALUES (1);
INSERT INTO tbllevel (level_id) VALUES (2);
INSERT INTO tbllevel (level_id) VALUES (3);


INSERT INTO tblmeal (meal_id, meal) VALUES (1, 'завтрак');
INSERT INTO tblmeal (meal_id, meal) VALUES (2, 'обед');
INSERT INTO tblmeal (meal_id, meal) VALUES (3, 'ужин');
-- ALTER SEQUENCE tblmeal_meal_id_seq RESTART WITH 4;

INSERT INTO tblmeasurement (measurement_id, name) VALUES (1, 'гр.');
INSERT INTO tblmeasurement (measurement_id, name) VALUES (2, 'мл.');
INSERT INTO tblmeasurement (measurement_id, name) VALUES (3, 'шт.');
-- ALTER SEQUENCE tblmeasurement_measurement_id_seq RESTART WITH 3;

INSERT INTO tblnutritionalinformation (nutrition_information_id, name) VALUES (1, 'жир');
INSERT INTO tblnutritionalinformation (nutrition_information_id, name) VALUES (2, 'белок');
INSERT INTO tblnutritionalinformation (nutrition_information_id, name) VALUES (3, 'углевод');
-- ALTER SEQUENCE tblnutritionalinformation_nutrition_information_id_seq RESTART WITH 4;

INSERT INTO tblingredientnutritionaninformation (rni_id, amount, ingredient_id, ni_id) VALUES (1, 1, 1, 2);
INSERT INTO tblingredientnutritionaninformation (rni_id, amount, ingredient_id, ni_id) VALUES (2, 1, 1, 3);
INSERT INTO tblingredientnutritionaninformation (rni_id, amount, ingredient_id, ni_id) VALUES (3, 1, 1, 1);
INSERT INTO tblingredientnutritionaninformation (rni_id, amount, ingredient_id, ni_id) VALUES (4, 1, 2, 3);
INSERT INTO tblingredientnutritionaninformation (rni_id, amount, ingredient_id, ni_id) VALUES (5, 1, 2, 1);
INSERT INTO tblingredientnutritionaninformation (rni_id, amount, ingredient_id, ni_id) VALUES (6, 1, 2, 2);
INSERT INTO tblingredientnutritionaninformation (rni_id, amount, ingredient_id, ni_id) VALUES (7, 1, 3, 1);
INSERT INTO tblingredientnutritionaninformation (rni_id, amount, ingredient_id, ni_id) VALUES (8, 1, 3, 3);
INSERT INTO tblingredientnutritionaninformation (rni_id, amount, ingredient_id, ni_id) VALUES (9, 1, 3, 2);
INSERT INTO tblingredientnutritionaninformation (rni_id, amount, ingredient_id, ni_id) VALUES (10, 1, 4, 2);
INSERT INTO tblingredientnutritionaninformation (rni_id, amount, ingredient_id, ni_id) VALUES (11, 1, 4, 3);
INSERT INTO tblingredientnutritionaninformation (rni_id, amount, ingredient_id, ni_id) VALUES (12, 1, 4, 1);
-- ALTER SEQUENCE tblingredientnutritionaninformation_rni_id_seq RESTART WITH 13;
INSERT INTO tblrecipe(name,description,instructions,cooktime,imagepath,rating,level_id,cuisine_id)
    VALUES ('testRecipe','testDescription','testInstructions',30,'testImagePath',5,1,1);
INSERT INTO tblrecipecourse(recipe_id, course_id) VALUES (1,1);
INSERT INTO tblrecipefoodcategory(recipe_id, food_category_id) VALUES (1,1);
INSERT INTO tblrecipemeals(recipe_id, meal_id) VALUES (1,1);
INSERT INTO tblrecipeingredient(amount, ingredient_id, measurement_id, recipe_id)
    VALUES (300,1,1,1);
INSERT INTO mng_menu (menu_id) values(default) ;
INSERT INTO mng_mealrecipe(meal_id, menu_id) VALUES (1, 1);
INSERT INTO mng_mealreciperecipe(mealrecipe_id, recipe_id) VALUES (1,1);
INSERT INTO mng_dailymenu (date, menu_id) VALUES (now(),1);

INSERT INTO tblrecipe(name,description,instructions,cooktime,imagepath,rating,level_id,cuisine_id)
VALUES ('Фахитас с курицей и фасолью','Фахитас с курицей и фасолью',
        'В разогретом масле обжариваем нарезанный болгарский перец, добавляем нарезанное кубиками куриное филе, слегка обжариваем, солим, добавляем нарезанный перец чили и консервированную в соусе чили фасоль, закрываем крышкой и тушим до готовности в течение 10 минут. Подаем с лавашом и сметаной.',
        30,'',5,1,3);
INSERT INTO tblrecipefoodcategory(recipe_id, food_category_id) VALUES (2,5);
INSERT INTO tblrecipemeals(recipe_id, meal_id) VALUES (2,2);
INSERT INTO tblrecipemeals(recipe_id, meal_id) VALUES (2,3);
INSERT INTO tblrecipeingredient(amount, ingredient_id, measurement_id, recipe_id)
VALUES (3,747,3,2);
INSERT INTO tblrecipeingredient(amount, ingredient_id, measurement_id, recipe_id)
VALUES (1000,429,1,2);
INSERT INTO tblrecipeingredient(amount, ingredient_id, measurement_id, recipe_id)
VALUES (4,754,3,2);
INSERT INTO tblrecipeingredient(amount, ingredient_id, measurement_id, recipe_id)
VALUES (0,939,1,2);
INSERT INTO tblrecipeingredient(amount, ingredient_id, measurement_id, recipe_id)
VALUES (800,1190,1,2);
INSERT INTO tblrecipeingredient(amount, ingredient_id, measurement_id, recipe_id)
VALUES (0,550,1,2);