
DROP TABLE IF EXISTS tblcourse CASCADE;
CREATE TABLE tblcourse (
                                  course_id bigserial NOT NULL,
                                  course character varying(255),
                                  CONSTRAINT tblcourse_pkey PRIMARY KEY (course_id)
);
DROP TABLE IF EXISTS tblcuisine CASCADE;
CREATE TABLE tblcuisine (
                                   cuisine_id bigserial NOT NULL,
                                   cuisine character varying(255),
                                   CONSTRAINT tblcuisine_pkey PRIMARY KEY (cuisine_id)
);
DROP TABLE IF EXISTS tblfoodcategory CASCADE;
CREATE TABLE tblfoodcategory (
                                        food_category_id bigserial NOT NULL,
                                        food_category character varying(255),
                                        CONSTRAINT tblfoodcategory_pkey PRIMARY KEY (food_category_id)
);

DROP TABLE IF EXISTS tblingredient CASCADE;
CREATE TABLE tblingredient (
                                      ingredient_id bigserial NOT NULL,
                                      name character varying(255),
                                      CONSTRAINT tblingredient_pkey PRIMARY KEY (ingredient_id)
);

DROP TABLE IF EXISTS tblnutritionalinformation CASCADE;
CREATE TABLE tblnutritionalinformation (
                                                  nutrition_information_id bigserial NOT NULL,
                                                  name character varying(255),
                                                  CONSTRAINT tblnutritionalinformation_pkey PRIMARY KEY (nutrition_information_id)
);

DROP TABLE IF EXISTS tblingredientnutritionaninformation CASCADE;
CREATE TABLE tblingredientnutritionaninformation (
                                                            rni_id bigserial NOT NULL,
                                                            amount real,
                                                            ingredient_id bigint,
                                                            ni_id bigint,
                                                            CONSTRAINT tblingredientnutritionaninformation_pkey PRIMARY KEY (rni_id),
                                                            CONSTRAINT fk_ni_id FOREIGN KEY (ni_id)
                                                                REFERENCES tblnutritionalinformation(nutrition_information_id)
);
DROP TABLE IF EXISTS tbllevel CASCADE;
CREATE TABLE tbllevel (
                                 level_id bigserial NOT NULL,
                                 CONSTRAINT tbllevel_pkey PRIMARY KEY (level_id)
);

DROP TABLE IF EXISTS tblmeal CASCADE;
CREATE TABLE tblmeal (
                                meal_id bigserial NOT NULL,
                                meal character varying(255),
                                CONSTRAINT tblmeal_pkey PRIMARY KEY (meal_id),
                                CONSTRAINT fk_meal_id_meal_id FOREIGN KEY (meal_id) REFERENCES tblmeal(meal_id)
);

DROP TABLE IF EXISTS tblmeasurement CASCADE;
CREATE TABLE tblmeasurement (
                                       measurement_id bigserial NOT NULL,
                                       name character varying(255),
                                       CONSTRAINT tblmeasurement_pkey PRIMARY KEY (measurement_id)
--                                        CONSTRAINT fk_measurement_id FOREIGN KEY (measurement_id)
--                                            REFERENCES tblmeasurement(measurement_id)
);

DROP TABLE IF EXISTS tblrecipe CASCADE;
CREATE TABLE tblrecipe (
                                  recipe_id bigserial NOT NULL,
                                  cooktime integer,
                                  cuisine_id integer,
                                  description character varying(255),
                                  imagepath character varying(255),
                                  instructions text,
                                  level_id integer,
                                  name character varying(255),
                                  rating integer,
                                  CONSTRAINT tblrecipe_pkey PRIMARY KEY (recipe_id),
                                  CONSTRAINT fk_level_id FOREIGN KEY (level_id)
                                      REFERENCES tbllevel(level_id),
                                  CONSTRAINT fk_cuisine_id FOREIGN KEY (cuisine_id)
                                      REFERENCES tblcuisine(cuisine_id)
);


DROP TABLE IF EXISTS tblrecipecourse CASCADE;
CREATE TABLE tblrecipecourse (
                                        recipe_id bigint NOT NULL,
                                        course_id bigint NOT NULL,
                                        CONSTRAINT tblrecipecourse_pkey PRIMARY KEY (recipe_id, course_id),
                                        CONSTRAINT fk_course_id FOREIGN KEY (course_id)
                                            REFERENCES tblcourse(course_id),
                                        CONSTRAINT fk_recipe_id FOREIGN KEY (recipe_id)
                                            REFERENCES tblrecipe(recipe_id)
);
DROP TABLE IF EXISTS tblrecipefoodcategory CASCADE;
CREATE TABLE tblrecipefoodcategory (
                                              recipe_id bigint NOT NULL,
                                              food_category_id bigint NOT NULL,
                                              CONSTRAINT tblrecipefoodcategory_pkey
                                                  PRIMARY KEY (recipe_id, food_category_id),
                                              CONSTRAINT fk_recipe_recipe_recipe_recipe_id FOREIGN KEY (recipe_id)
                                                  REFERENCES tblrecipe(recipe_id),
                                              CONSTRAINT fk_food_category_id FOREIGN KEY (food_category_id)
                                                  REFERENCES tblfoodcategory(food_category_id)
);
DROP TABLE IF EXISTS tblrecipeingredient CASCADE;
CREATE TABLE tblrecipeingredient (
                                     recipe_ingredient_id bigserial NOT NULL,
                                     amount integer,
                                     ingredient_id bigint NOT NULL,
                                     measurement_id bigint,
                                     recipe_id bigint NOT NULL,
                                     CONSTRAINT tblrecipeingredient_pkey PRIMARY KEY (recipe_ingredient_id),
                                     CONSTRAINT fk_recipe_recipe_id FOREIGN KEY (recipe_id)
                                         REFERENCES tblrecipe(recipe_id),
                                     CONSTRAINT fk_ingredient_id_ingredient_id FOREIGN KEY (ingredient_id)
                                         REFERENCES tblingredient(ingredient_id)
);
DROP TABLE IF EXISTS tblrecipemeals CASCADE;
CREATE TABLE tblrecipemeals (
                                recipe_id bigint NOT NULL,
                                meal_id bigint NOT NULL,
                                CONSTRAINT tblrecipemeals_pkey PRIMARY KEY (recipe_id, meal_id),
                                CONSTRAINT fk_recipe_recipe_recipe_id FOREIGN KEY (recipe_id)
                                    REFERENCES tblrecipe(recipe_id),
                                CONSTRAINT fk_meal_id FOREIGN KEY (meal_id)
                                    REFERENCES tblmeal(meal_id)
);

DROP TABLE IF EXISTS mng_menu CASCADE;
CREATE TABLE mng_menu (
                          menu_id bigserial NOT NULL,
                          CONSTRAINT mng_menu_pkey PRIMARY KEY (menu_id)
);

DROP TABLE IF EXISTS mng_mealrecipe CASCADE;
CREATE TABLE mng_mealrecipe(
                               mealrecipe_id bigserial NOT NULL,
                               meal_id       bigint    NOT NULL,
                               menu_id  bigint    NULL,
                               CONSTRAINT mng_mealrecipe_pkey PRIMARY KEY (mealrecipe_id),
                               CONSTRAINT meal_id_fk FOREIGN KEY (meal_id)
                                   REFERENCES tblmeal (meal_id),
                               CONSTRAINT menu_id_fk FOREIGN KEY (menu_id)
                                   REFERENCES mng_menu (menu_id)
);

DROP TABLE IF EXISTS mng_dailymenu CASCADE;
CREATE TABLE mng_dailymenu (
                               dailymenu_id bigserial NOT NULL,
                               date date NOT NULL,
                               menu_id bigint NOT NULL,
                               CONSTRAINT mng_dailymenu_pkey PRIMARY KEY (dailymenu_id),
                               CONSTRAINT dailymenu_menu_id_fk FOREIGN KEY (menu_id)
                                   REFERENCES mng_menu (menu_id)
);

DROP TABLE IF EXISTS mng_mealreciperecipe CASCADE;
CREATE TABLE mng_mealreciperecipe(
                                     mealrecipe_id bigint NOT NULL,
                                     recipe_id bigint NOT NULL,
                                     CONSTRAINT mng_mealreciperecipe_pkey PRIMARY KEY (mealrecipe_id,recipe_id),
                                     CONSTRAINT mealrecipe_id_fk FOREIGN KEY (mealrecipe_id)
                                         REFERENCES mng_mealrecipe(mealrecipe_id),
                                     CONSTRAINT recipe_id_fk FOREIGN KEY (recipe_id)
                                         REFERENCES tblrecipe(recipe_id)
);