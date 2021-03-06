INSERT INTO public.tblcourse (course_id, course) VALUES (1, 'суп');
INSERT INTO public.tblcourse (course_id, course) VALUES (2, 'основное блюдо');
INSERT INTO public.tblcourse (course_id, course) VALUES (3, 'салат');
INSERT INTO public.tblcourse (course_id, course) VALUES (4, 'гарнир');
INSERT INTO public.tblcourse (course_id, course) VALUES (5, 'десерт');
INSERT INTO public.tblcourse (course_id, course) VALUES (6, 'напиток');
ALTER SEQUENCE tblcourse_course_id_seq RESTART WITH 7;

INSERT INTO public.tblcuisine (cuisine_id, cuisine) VALUES (1, 'европейская');
INSERT INTO public.tblcuisine (cuisine_id, cuisine) VALUES (2, 'средиземноморская');
INSERT INTO public.tblcuisine (cuisine_id, cuisine) VALUES (3, 'мексиканская');
INSERT INTO public.tblcuisine (cuisine_id, cuisine) VALUES (4, 'русская');
ALTER SEQUENCE tblcuisine_cuisine_id_seq RESTART WITH 5;

INSERT INTO public.tblfoodcategory (food_category_id, food_category) VALUES (1, 'молочные продукты');
INSERT INTO public.tblfoodcategory (food_category_id, food_category) VALUES (2, 'овощи');
INSERT INTO public.tblfoodcategory (food_category_id, food_category) VALUES (3, 'злаковые');
INSERT INTO public.tblfoodcategory (food_category_id, food_category) VALUES (4, 'фрукты');
INSERT INTO public.tblfoodcategory (food_category_id, food_category) VALUES (5, 'птица');
INSERT INTO public.tblfoodcategory (food_category_id, food_category) VALUES (6, 'мясо');
ALTER SEQUENCE tblfoodcategory_food_category_id_seq RESTART WITH 7;

INSERT INTO public.tblingredient (ingredient_id, name) VALUES (1, 'рис');
INSERT INTO public.tblingredient (ingredient_id, name) VALUES (2, 'лук');
INSERT INTO public.tblingredient (ingredient_id, name) VALUES (3, 'морковь');
INSERT INTO public.tblingredient (ingredient_id, name) VALUES (4, 'консервированный тунец');
ALTER SEQUENCE tblingredient_ingredient_id_seq RESTART WITH 5;

INSERT INTO public.tbllevel (level_id) VALUES (1);
INSERT INTO public.tbllevel (level_id) VALUES (2);
INSERT INTO public.tbllevel (level_id) VALUES (3);


INSERT INTO public.tblmeal (meal_id, meal) VALUES (1, 'завтрак');
INSERT INTO public.tblmeal (meal_id, meal) VALUES (2, 'обед');
INSERT INTO public.tblmeal (meal_id, meal) VALUES (3, 'ужин');
ALTER SEQUENCE tblmeal_meal_id_seq RESTART WITH 4;

INSERT INTO public.tblmeasurement (measurement_id, name) VALUES (1, 'гр.');
INSERT INTO public.tblmeasurement (measurement_id, name) VALUES (2, 'мл.');
ALTER SEQUENCE tblmeasurement_measurement_id_seq RESTART WITH 3;

INSERT INTO public.tblnutritionalinformation (nutrition_information_id, name) VALUES (1, 'жир');
INSERT INTO public.tblnutritionalinformation (nutrition_information_id, name) VALUES (2, 'белок');
INSERT INTO public.tblnutritionalinformation (nutrition_information_id, name) VALUES (3, 'углевод');
ALTER SEQUENCE tblnutritionalinformation_nutrition_information_id_seq RESTART WITH 4;

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
ALTER SEQUENCE tblingredientnutritionaninformation_rni_id_seq RESTART WITH 13;



