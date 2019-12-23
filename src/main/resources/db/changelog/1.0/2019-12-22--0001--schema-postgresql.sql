DROP SEQUENCE IF EXISTS hibernate_sequence CASCADE;
CREATE SEQUENCE public.hibernate_sequence START WITH 1 INCREMENT BY 1 NO MINVALUE NO MAXVALUE CACHE 1;

DROP SEQUENCE IF EXISTS public.tblcourse_course_id_seq CASCADE;
CREATE SEQUENCE public.tblcourse_course_id_seq  START WITH 1 INCREMENT BY 1 NO MINVALUE NO MAXVALUE CACHE 1;
DROP TABLE IF EXISTS public.tblcourse CASCADE;
CREATE TABLE public.tblcourse (
                                  course_id bigint NOT NULL DEFAULT nextval('public.tblcourse_course_id_seq'::regclass),
                                  course character varying(255),
                                  CONSTRAINT tblcourse_pkey PRIMARY KEY (course_id)
);

DROP SEQUENCE IF EXISTS public.tblcuisine_cuisine_id_seq CASCADE;
CREATE SEQUENCE public.tblcuisine_cuisine_id_seq START WITH 1 INCREMENT BY 1 NO MINVALUE NO MAXVALUE CACHE 1;
DROP TABLE IF EXISTS public.tblcuisine CASCADE;
CREATE TABLE public.tblcuisine (
                                   cuisine_id bigint NOT NULL DEFAULT nextval('public.tblcuisine_cuisine_id_seq'::regclass),
                                   cuisine character varying(255),
                                   CONSTRAINT tblcuisine_pkey PRIMARY KEY (cuisine_id)
);

DROP SEQUENCE IF EXISTS public.tblfoodcategory_food_category_id_seq CASCADE;
CREATE SEQUENCE public.tblfoodcategory_food_category_id_seq START WITH 1 INCREMENT BY 1 NO MINVALUE NO MAXVALUE CACHE 1;
DROP TABLE IF EXISTS public.tblfoodcategory CASCADE;
CREATE TABLE public.tblfoodcategory (
                                    food_category_id bigint NOT NULL DEFAULT nextval('public.tblfoodcategory_food_category_id_seq'::regclass),
                                    food_category character varying(255),
                                    CONSTRAINT tblfoodcategory_pkey PRIMARY KEY (food_category_id)
);

DROP SEQUENCE IF EXISTS public.tblingredient_id_seq CASCADE;
CREATE SEQUENCE public.tblingredient_id_seq START WITH 1 INCREMENT BY 1 NO MINVALUE NO MAXVALUE CACHE 1;
DROP TABLE IF EXISTS public.tblingredient CASCADE;
CREATE TABLE public.tblingredient (
                                   ingredient_id bigint NOT NULL DEFAULT nextval('public.tblingredient_id_seq'::regclass),
                                   name character varying(255),
                                   CONSTRAINT tblingredient_pkey PRIMARY KEY (ingredient_id)
);

DROP SEQUENCE IF EXISTS public.tblnutritionalinformation_nutrition_information_id_seq CASCADE;
CREATE SEQUENCE public.tblnutritionalinformation_nutrition_information_id_seq START WITH 1 INCREMENT BY 1 NO MINVALUE NO MAXVALUE CACHE 1;
DROP TABLE IF EXISTS public.tblnutritionalinformation CASCADE;
CREATE TABLE public.tblnutritionalinformation (
                                  nutrition_information_id bigint NOT NULL DEFAULT nextval('public.tblnutritionalinformation_nutrition_information_id_seq'::regclass),
                                  name character varying(255),
                                  CONSTRAINT tblnutritionalinformation_pkey PRIMARY KEY (nutrition_information_id)
);

DROP SEQUENCE IF EXISTS public.tblingredientnutritionaninformation_rni_id_seq CASCADE;
CREATE SEQUENCE public.tblingredientnutritionaninformation_rni_id_seq START WITH 1 INCREMENT BY 1 NO MINVALUE NO MAXVALUE CACHE 1;
DROP TABLE IF EXISTS public.tblingredientnutritionaninformation CASCADE;
CREATE TABLE public.tblingredientnutritionaninformation (
                                 rni_id bigint NOT NULL DEFAULT nextval('public.tblingredientnutritionaninformation_rni_id_seq'::regclass),
                                 amount real,
                                 ingredient_id bigint,
                                 ni_id bigint,
                                 CONSTRAINT tblingredientnutritionaninformation_pkey PRIMARY KEY (rni_id),
                                 CONSTRAINT fk_ni_id FOREIGN KEY (ni_id)
                                     REFERENCES public.tblnutritionalinformation(nutrition_information_id)
);
DROP SEQUENCE IF EXISTS public.tbllevel_level_id_seq CASCADE;
CREATE SEQUENCE public.tbllevel_level_id_seq START WITH 1 INCREMENT BY 1 NO MINVALUE NO MAXVALUE CACHE 1;
DROP TABLE IF EXISTS public.tbllevel CASCADE;
CREATE TABLE public.tbllevel (
                                level_id bigint NOT NULL DEFAULT nextval('public.tbllevel_level_id_seq'::regclass),
                                CONSTRAINT tbllevel_pkey PRIMARY KEY (level_id)
);

DROP SEQUENCE IF EXISTS tblmeal_meal_id_seq CASCADE;
CREATE SEQUENCE public.tblmeal_meal_id_seq START WITH 1 INCREMENT BY 1 NO MINVALUE NO MAXVALUE CACHE 1;
DROP TABLE IF EXISTS tblmeal CASCADE;
CREATE TABLE public.tblmeal (
                                meal_id bigint NOT NULL DEFAULT nextval('public.tblmeal_meal_id_seq'::regclass),
                                meal character varying(255),
                                CONSTRAINT tblmeal_pkey PRIMARY KEY (meal_id)
);

DROP SEQUENCE IF EXISTS public.tblmeasurement_measurement_id_seq CASCADE;
CREATE SEQUENCE public.tblmeasurement_measurement_id_seq START WITH 1 INCREMENT BY 1 NO MINVALUE NO MAXVALUE CACHE 1;
DROP TABLE IF EXISTS tblmeasurement CASCADE;
CREATE TABLE public.tblmeasurement (
                               measurement_id bigint NOT NULL DEFAULT nextval('public.tblmeasurement_measurement_id_seq'::regclass),
                               name character varying(255),
                               CONSTRAINT tblmeasurement_pkey PRIMARY KEY (measurement_id)
);

DROP SEQUENCE IF EXISTS public.tblrecipe_recipe_id_seq CASCADE;
CREATE SEQUENCE public.tblrecipe_recipe_id_seq START WITH 1 INCREMENT BY 1 NO MINVALUE NO MAXVALUE CACHE 1;
DROP TABLE IF EXISTS tblrecipe CASCADE;
CREATE TABLE public.tblrecipe (
                              recipe_id bigint NOT NULL DEFAULT nextval('public.tblrecipe_recipe_id_seq'::regclass),
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
                                  REFERENCES public.tbllevel(level_id),
                              CONSTRAINT fk_cuisine_id FOREIGN KEY (cuisine_id)
                                  REFERENCES public.tblcuisine(cuisine_id)
);


DROP TABLE IF EXISTS public.tblrecipecourse CASCADE;
CREATE TABLE public.tblrecipecourse (
                             recipe_id bigint NOT NULL,
                             course_id bigint NOT NULL,
                             CONSTRAINT tblrecipecourse_pkey PRIMARY KEY (recipe_id, course_id),
                             CONSTRAINT fk_course_id FOREIGN KEY (course_id)
                                 REFERENCES public.tblcourse(course_id),
                             CONSTRAINT fk_recipe_id FOREIGN KEY (recipe_id)
                                 REFERENCES public.tblrecipe(recipe_id)
);
DROP TABLE IF EXISTS public.tblrecipefoodcategory CASCADE;
CREATE TABLE public.tblrecipefoodcategory (
                              recipe_id bigint NOT NULL,
                              food_category_id bigint NOT NULL,
                              CONSTRAINT tblrecipefoodcategory_pkey
                                  PRIMARY KEY (recipe_id, food_category_id),
                              CONSTRAINT fk_recipe_id FOREIGN KEY (recipe_id)
                                  REFERENCES public.tblrecipe(recipe_id),
                              CONSTRAINT fk_food_category_id FOREIGN KEY (food_category_id)
                                  REFERENCES public.tblfoodcategory(food_category_id)
);
DROP SEQUENCE IF EXISTS public.tblrecipeingredient_id_seq CASCADE;
CREATE SEQUENCE public.tblrecipeingredient_id_seq START WITH 1 INCREMENT BY 1 NO MINVALUE NO MAXVALUE CACHE 1;
DROP TABLE IF EXISTS tblrecipeingredient CASCADE;
CREATE TABLE public.tblrecipeingredient (
                              recipe_ingredient_id bigint NOT NULL DEFAULT nextval('public.tblrecipeingredient_id_seq'::regclass),
                              amount integer,
                              ingredient_id bigint NOT NULL,
                              measurement_id bigint,
                              recipe_id bigint NOT NULL,
                              CONSTRAINT tblrecipeingredient_pkey PRIMARY KEY (recipe_ingredient_id),
                              CONSTRAINT fk_recipe_id FOREIGN KEY (recipe_id)
                                  REFERENCES public.tblrecipe(recipe_id),
                              CONSTRAINT fk_ingredient_id FOREIGN KEY (ingredient_id)
                                  REFERENCES public.tblingredient(ingredient_id)
);
DROP TABLE IF EXISTS tblrecipemeals CASCADE;
CREATE TABLE public.tblrecipemeals (
                              recipe_id bigint NOT NULL,
                              meal_id bigint NOT NULL,
                              CONSTRAINT tblrecipemeals_pkey PRIMARY KEY (recipe_id, meal_id),
                              CONSTRAINT fk_recipe_id FOREIGN KEY (recipe_id)
                                  REFERENCES public.tblrecipe(recipe_id),
                              CONSTRAINT fk_meal_id FOREIGN KEY (meal_id)
                                  REFERENCES public.tblmeal(meal_id)
);

DROP SEQUENCE IF EXISTS public.mng_menu_id_seq CASCADE;
CREATE SEQUENCE public.mng_menu_id_seq START WITH 1 INCREMENT BY 1 NO MINVALUE NO MAXVALUE CACHE 1;
DROP TABLE IF EXISTS public.mng_menu CASCADE;
CREATE TABLE public.mng_menu (
                          menu_id bigint NOT NULL DEFAULT nextval('public.mng_menu_id_seq'::regclass),
                          CONSTRAINT mng_menu_pkey PRIMARY KEY (menu_id)
);

DROP SEQUENCE IF EXISTS public.mng_mealrecipe_id_seq CASCADE;
CREATE SEQUENCE public.mng_mealrecipe_id_seq  START WITH 1 INCREMENT BY 1 NO MINVALUE NO MAXVALUE CACHE 1;
DROP TABLE IF EXISTS public.mng_mealrecipe CASCADE;
CREATE TABLE public.mng_mealrecipe(
                        mealrecipe_id bigint NOT NULL DEFAULT nextval('public.mng_mealrecipe_id_seq'::regclass),
                        meal_id       bigint    NOT NULL,
                        menu_id  bigint    NULL,
                        CONSTRAINT mng_mealrecipe_pkey PRIMARY KEY (mealrecipe_id),
                        CONSTRAINT meal_id_fk FOREIGN KEY (meal_id)
                            REFERENCES public.tblmeal (meal_id),
                        CONSTRAINT menu_id_fk FOREIGN KEY (menu_id)
                            REFERENCES public.mng_menu (menu_id)
);

DROP SEQUENCE IF EXISTS public.mng_dailymenu_id_seq CASCADE;
CREATE SEQUENCE public.mng_dailymenu_id_seq  START WITH 1 INCREMENT BY 1 NO MINVALUE NO MAXVALUE CACHE 1;
DROP TABLE IF EXISTS public.mng_dailymenu CASCADE;
CREATE TABLE public.mng_dailymenu (
                               dailymenu_id bigint NOT NULL DEFAULT nextval('public.mng_dailymenu_id_seq'::regclass),
                               date date NOT NULL,
                               menu_id bigint NOT NULL,
                               CONSTRAINT mng_dailymenu_pkey PRIMARY KEY (dailymenu_id),
                               CONSTRAINT dailymenu_menu_id_fk FOREIGN KEY (menu_id)
                                   REFERENCES public.mng_menu (menu_id)
);

DROP TABLE IF EXISTS public.mng_mealreciperecipe CASCADE;
CREATE TABLE public.mng_mealreciperecipe(
                                mealrecipe_id bigint NOT NULL,
                                recipe_id bigint NOT NULL,
                                CONSTRAINT mng_mealreciperecipe_pkey PRIMARY KEY (mealrecipe_id,recipe_id),
                                CONSTRAINT mealrecipe_id_fk FOREIGN KEY (mealrecipe_id)
                                    REFERENCES public.mng_mealrecipe(mealrecipe_id),
                                CONSTRAINT recipe_id_fk FOREIGN KEY (recipe_id)
                                    REFERENCES public.tblrecipe(recipe_id)
);