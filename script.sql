CREATE DOMAIN EMAIL AS TEXT
	CONSTRAINT email_check CHECK (value ~
								  '^[a-zA-Z0-9.!#$%&''*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$'::TEXT);

COMMENT ON TYPE EMAIL IS 'Typ tekstowy w formacie adresu email';

ALTER DOMAIN EMAIL OWNER TO postgres;

CREATE TABLE users
(
	id            SERIAL       NOT NULL
		CONSTRAINT users_pkey
			PRIMARY KEY,
	name          VARCHAR(200) NOT NULL,
	email         EMAIL        NOT NULL
		CONSTRAINT users_email_key
			UNIQUE,
	password_hash CHAR(60)     NOT NULL
		CONSTRAINT users_password_hash_check
			CHECK (length(password_hash) = 60),
	avatar        BYTEA
);

ALTER TABLE users
	OWNER TO postgres;

CREATE TABLE admins
(
	id INTEGER NOT NULL
		CONSTRAINT admins_pkey
			PRIMARY KEY
		CONSTRAINT admins_id_fkey
			REFERENCES users
			ON UPDATE CASCADE ON DELETE CASCADE
);

ALTER TABLE admins
	OWNER TO postgres;

CREATE TABLE employees
(
	id                INTEGER NOT NULL
		CONSTRAINT employees_pkey
			PRIMARY KEY
		CONSTRAINT employees_id_fkey
			REFERENCES users
			ON UPDATE CASCADE ON DELETE CASCADE,
	www               TEXT,
	scientific_titles VARCHAR(50)
);

ALTER TABLE employees
	OWNER TO postgres;

CREATE TABLE students
(
	id INTEGER NOT NULL
		CONSTRAINT students_pkey
			PRIMARY KEY
		CONSTRAINT students_id_fkey
			REFERENCES users
			ON UPDATE CASCADE ON DELETE CASCADE
);

ALTER TABLE students
	OWNER TO postgres;

CREATE TABLE courses
(
	id          SERIAL       NOT NULL
		CONSTRAINT courses_pkey
			PRIMARY KEY,
	name        VARCHAR(200) NOT NULL,
	coordinator INTEGER      NOT NULL
		CONSTRAINT courses_coordinator_fkey
			REFERENCES employees
			ON UPDATE CASCADE ON DELETE CASCADE,
	description TEXT
);

ALTER TABLE courses
	OWNER TO postgres;

CREATE TABLE test
(
	test TEXT
);

ALTER TABLE test
	OWNER TO postgres;

CREATE TABLE classes
(
	id          SERIAL  NOT NULL
		CONSTRAINT classes_pkey
			PRIMARY KEY,
	course      INTEGER NOT NULL,
	coordinator INTEGER NOT NULL
		CONSTRAINT classes_coordinator_fkey
			REFERENCES employees
			ON UPDATE CASCADE ON DELETE CASCADE,
	semester    CHAR(5)
		CONSTRAINT classes_semester_check
			CHECK (semester ~ similar_escape('2[0-9]{3}[LZ]'::TEXT, NULL::TEXT))
);

COMMENT ON COLUMN classes.semester IS 'Numer semestru. Jest to rok z doklejonym L lub Z na końcu w zależności od tego, czy to semestr letni czy zimowy';

ALTER TABLE classes
	OWNER TO postgres;

CREATE FUNCTION user_exists(INTEGER) RETURNS BOOLEAN
	LANGUAGE SQL
AS
$$
SELECT EXISTS(SELECT id FROM users WHERE id = $1);
$$;

ALTER FUNCTION user_exists(INTEGER) OWNER TO postgres;

CREATE FUNCTION get_avatar(_id INTEGER) RETURNS BYTEA
	LANGUAGE plpgsql
AS
$$
BEGIN
	IF NOT user_exists(_id) THEN
		RAISE EXCEPTION 'USER DOES NOT EXISTS';
	END IF;
	RETURN (SELECT avatar FROM users WHERE id = _id);
END;
$$;

ALTER FUNCTION get_avatar(INTEGER) OWNER TO postgres;

CREATE FUNCTION get_id(_email EMAIL) RETURNS INTEGER
	LANGUAGE plpgsql
AS
$$
DECLARE
	_id INT;
BEGIN
	SELECT id FROM users WHERE email = _email INTO _id;
	IF _id IS NULL THEN
		RAISE EXCEPTION 'User does not exists';
	END IF;
	RETURN _id;
END;
$$;

ALTER FUNCTION get_id(EMAIL) OWNER TO postgres;

CREATE FUNCTION change_password(_email EMAIL, _new_password_hash CHARACTER) RETURNS VOID
	LANGUAGE plpgsql
AS
$$
BEGIN
	PERFORM change_password(get_id(_email), _new_password_hash);
END;
$$;

ALTER FUNCTION change_password(EMAIL, CHAR) OWNER TO postgres;

CREATE FUNCTION change_password(_id INTEGER, _new_password_hash CHARACTER) RETURNS VOID
	LANGUAGE plpgsql
AS
$$
BEGIN
	SELECT id FROM users WHERE id = _id INTO _id;
	IF _id IS NULL THEN
		RAISE EXCEPTION 'User does not exists';
	END IF;
	IF length(_new_password_hash) != 60 THEN
		RAISE EXCEPTION 'BCRYPT Hash must be 60 characters long';
	END IF;
	UPDATE users SET password_hash = _new_password_hash WHERE id = _id;
END;
$$;

ALTER FUNCTION change_password(INTEGER, CHAR) OWNER TO postgres;

CREATE FUNCTION set_avatar(_id INTEGER, _avatar BYTEA) RETURNS VOID
	LANGUAGE plpgsql
AS
$$
BEGIN
	IF NOT user_exists(_id) THEN
		RAISE EXCEPTION 'User does not exists';
	END IF;
	UPDATE users SET avatar = _avatar WHERE id = _id;
END;
$$;

ALTER FUNCTION set_avatar(INTEGER, BYTEA) OWNER TO postgres;

CREATE FUNCTION is_admin(_id INTEGER) RETURNS INTEGER
	LANGUAGE plpgsql
AS
$$
BEGIN
	IF NOT user_exists(_id) THEN
		RETURN -1;
	END IF;
	IF EXISTS(SELECT id FROM admins WHERE id = _id) THEN
		RETURN 1;
	END IF;
	RETURN 0;
END;
$$;

COMMENT ON FUNCTION is_admin(INTEGER) IS 'Sprawdza czy użtownik o podanym id jest administratorem. Zwraca 0 kiedy nie jest, 1 kiedy jest lub -1 kiedy użytkownik o podanym id nie istnieje';

ALTER FUNCTION is_admin(INTEGER) OWNER TO postgres;

CREATE FUNCTION is_admin(_email EMAIL) RETURNS INTEGER
	LANGUAGE plpgsql
AS
$$
DECLARE
	_id INT;
BEGIN
	SELECT id FROM users WHERE email = _email INTO _id;
	IF _id IS NULL THEN
		RETURN -1;
	END IF;
	IF EXISTS(SELECT id FROM admins WHERE id = _id) THEN
		RETURN 1;
	ELSE
		RETURN 0;
	END IF;
END;
$$;

ALTER FUNCTION is_admin(EMAIL) OWNER TO postgres;

CREATE FUNCTION is_student(_email EMAIL) RETURNS INTEGER
	LANGUAGE plpgsql
AS
$$
DECLARE
	_id INT;
BEGIN
	SELECT id FROM users WHERE email = _email INTO _id;
	IF _id IS NULL THEN
		RETURN -1;
	END IF;
	IF EXISTS(SELECT id FROM students WHERE id = _id) THEN
		RETURN 1;
	END IF;
	RETURN 0;
END;
$$;

ALTER FUNCTION is_student(EMAIL) OWNER TO postgres;

CREATE FUNCTION is_student(_id INTEGER) RETURNS INTEGER
	LANGUAGE plpgsql
AS
$$
BEGIN
	SELECT id FROM users WHERE id = _id INTO _id;
	IF _id IS NULL THEN
		RETURN -1;
	END IF;
	IF EXISTS(SELECT id FROM students WHERE id = _id) THEN
		RETURN 1;
	END IF;
	RETURN 0;
END;
$$;

ALTER FUNCTION is_student(INTEGER) OWNER TO postgres;

CREATE FUNCTION is_employee(_id INTEGER) RETURNS INTEGER
	LANGUAGE plpgsql
AS
$$
BEGIN
	SELECT id FROM users WHERE id = _id INTO _id;
	IF _id IS NULL THEN
		RETURN -1;
	END IF;
	SELECT id FROM employees WHERE id = _id INTO _id;
	IF _id IS NULL THEN
		RETURN 0;
	END IF;
	RETURN 1;
END;
$$;

ALTER FUNCTION is_employee(INTEGER) OWNER TO postgres;

CREATE FUNCTION is_employee(_email EMAIL) RETURNS INTEGER
	LANGUAGE plpgsql
AS
$$
DECLARE
	_id INT;
BEGIN
	SELECT id FROM users WHERE email = _email INTO _id;
	IF _id IS NULL THEN
		RETURN -1;
	END IF;
	SELECT id FROM employees WHERE id = _id INTO _id;
	IF _id IS NULL THEN
		RETURN 0;
	END IF;
	RETURN 1;
END;
$$;

ALTER FUNCTION is_employee(EMAIL) OWNER TO postgres;

CREATE FUNCTION add_student(_email EMAIL) RETURNS INTEGER
	LANGUAGE plpgsql
AS
$$
DECLARE
	_id INT;
BEGIN
	SELECT id FROM users WHERE email = _email INTO _id;
	IF _id IS NULL THEN
		RETURN 1;
	END IF;
	IF EXISTS(SELECT id FROM students WHERE id = _id) THEN
		RETURN 0;
	ELSE
		INSERT INTO students (id) VALUES (_id);
		RETURN 0;
	END IF;
END;
$$;

COMMENT ON FUNCTION add_student(EMAIL) IS 'Tworzy nowego studenta w bazie danych, ale tylko w przypadku w którym użytkownik o podanym emailu istnieje. Zwraca 0 jeżeli użytkownik istnieje ale jeszcze nie był studentem i został właśnie na studenta mianowany lub gdy w bazie istnieje już student o takim emailu. Zwraca 1, gdy w bazie nie ma użytkownika o podanym emailu.';

ALTER FUNCTION add_student(EMAIL) OWNER TO postgres;

CREATE FUNCTION add_student(_email EMAIL, _name CHARACTER VARYING, _password_hash CHARACTER) RETURNS INTEGER
	LANGUAGE plpgsql
AS
$$
DECLARE
	_id INT;
BEGIN
	SELECT id FROM users WHERE email = _email INTO _id;
	IF _id IS NULL THEN
		INSERT INTO users (name, email, password_hash) VALUES (_name, _email, _password_hash);
		INSERT INTO students (id) VALUES (_id);
		RETURN 0;
	END IF;
	IF EXISTS(SELECT id FROM students WHERE id = _id) THEN
		RETURN 1;
	END IF;
	INSERT INTO students (id) VALUES (_id);
	RETURN 2;
END;
$$;

COMMENT ON FUNCTION add_student(EMAIL, VARCHAR, CHAR) IS 'Tworzy nowego studenta w bazie danych. Jeżeli w bazie danych isteniej użytkownik o takim emailu ale nie jest studentem, użytkownik dostaje miano studenta i zwracane jest 2. Jeżeli w bazie istenieje już taki student to zwracane jest 1. Jeżeli w bazie nie ma użytkownika o takim emailu, jest on tworzony i mianujemy go studentem';

ALTER FUNCTION add_student(EMAIL, VARCHAR, CHAR) OWNER TO postgres;

CREATE FUNCTION update_user_data(_email EMAIL, _name CHARACTER VARYING, _avatar BYTEA) RETURNS INTEGER
	LANGUAGE plpgsql
AS
$$
DECLARE
	_id INT;
BEGIN
	SELECT id FROM users WHERE email = _email INTO _id;
	IF _id IS NULL THEN
		RETURN 1;
	END IF;
	UPDATE users SET name = _name, avatar = _avatar WHERE id = _id;
	RETURN 0;
END;
$$;

ALTER FUNCTION update_user_data(EMAIL, VARCHAR, BYTEA) OWNER TO postgres;

CREATE FUNCTION update_user_avatar(_email EMAIL, _avatar BYTEA) RETURNS INTEGER
	LANGUAGE plpgsql
AS
$$
DECLARE
	_id INT;
BEGIN
	SELECT id FROM users WHERE email = _email INTO _id;
	IF _id IS NULL THEN
		RETURN 1;
	END IF;
	UPDATE users SET avatar = _avatar WHERE id = _id;
	RETURN 0;
END;
$$;

ALTER FUNCTION update_user_avatar(EMAIL, BYTEA) OWNER TO postgres;

CREATE FUNCTION update_user_name(_email EMAIL, _name CHARACTER VARYING) RETURNS INTEGER
	LANGUAGE plpgsql
AS
$$
DECLARE
	_id INT;
BEGIN
	SELECT id FROM users WHERE email = _email INTO _id;
	IF _id IS NULL THEN
		RETURN 1;
	END IF;
	UPDATE users SET name = _name WHERE id = _id;
	RETURN 0;
END;
$$;

ALTER FUNCTION update_user_name(EMAIL, VARCHAR) OWNER TO postgres;

CREATE FUNCTION add_employee(_email EMAIL, website TEXT) RETURNS INTEGER
	LANGUAGE plpgsql
AS
$$
DECLARE
	_id INT;
BEGIN
	SELECT id FROM users WHERE email = _email INTO _id;
	IF _id IS NULL THEN
		RETURN 1;
	END IF;
	IF EXISTS(SELECT id FROM employees WHERE id = _id) THEN
		RETURN 0;
	END IF;
	INSERT INTO employees (id, www) VALUES (_id, website);
	RETURN 0;
END;
$$;

ALTER FUNCTION add_employee(EMAIL, TEXT) OWNER TO postgres;

CREATE FUNCTION add_employee(_email EMAIL, _name CHARACTER VARYING, _password_hash CHARACTER,
							 website TEXT) RETURNS INTEGER
	LANGUAGE plpgsql
AS
$$
DECLARE
	_id INT;
BEGIN
	SELECT id FROM users WHERE email = _email INTO _id;
	IF _id IS NOT NULL THEN
		RETURN 1;
	END IF;
	IF length(_password_hash) != 60 THEN
		RETURN 2;
	END IF;
	INSERT INTO users (name, email, password_hash) VALUES (_name, _email, _password_hash);
	SELECT id FROM users WHERE email = _email INTO _id;
	PERFORM add_employee(_email, website);
	RETURN 0;
END;
$$;

ALTER FUNCTION add_employee(EMAIL, VARCHAR, CHAR, TEXT) OWNER TO postgres;

CREATE FUNCTION get_roles(_email EMAIL) RETURNS TEXT[]
	LANGUAGE plpgsql
AS
$$
DECLARE
	_id          INT;
	_temp_string TEXT;
BEGIN
	SELECT id FROM users WHERE email = _email INTO _id;
	IF _id IS NULL THEN
		RETURN _temp_string;
	END IF;
	IF EXISTS(SELECT id FROM students WHERE id = _id) THEN
		_temp_string := concat(_temp_string, ' ROLE_STUDENT');
	END IF;
	IF EXISTS(SELECT id FROM admins WHERE id = _id) THEN
		_temp_string := concat(_temp_string, ' ROLE_ADMIN');
	END IF;
	IF EXISTS(SELECT id FROM employees WHERE id = _id) THEN
		_temp_string := concat(_temp_string, ' ROLE_EMPLOYEE');
	END IF;
	IF length(_temp_string) > 1 THEN
		_temp_string := substr(_temp_string, 2);
	END IF;
	RETURN string_to_array(_temp_string, ' ');
END;
$$;

ALTER FUNCTION get_roles(EMAIL) OWNER TO postgres;

CREATE FUNCTION get_all_employees()
	RETURNS TABLE
			(
				id                INTEGER,
				name              CHARACTER VARYING,
				www               TEXT,
				email             EMAIL,
				scientific_titles CHARACTER VARYING
			)
	LANGUAGE SQL
AS
$$
SELECT u.id AS id, name, www, email, e.scientific_titles
FROM employees e
		 JOIN users u ON e.id = u.id
ORDER BY name;
$$;

ALTER FUNCTION get_all_employees() OWNER TO postgres;

CREATE FUNCTION get_all_employees_name_like(CHARACTER VARYING)
	RETURNS TABLE
			(
				id                INTEGER,
				name              CHARACTER VARYING,
				www               TEXT,
				email             EMAIL,
				scientific_titles CHARACTER VARYING
			)
	LANGUAGE SQL
AS
$$
SELECT u.id AS id, name, www, email, e.scientific_titles
FROM employees e
		 JOIN users u ON e.id = u.id
WHERE lower(name) LIKE lower('%' || lower(quote_ident($1)) || '%')
ORDER BY name;
$$;

ALTER FUNCTION get_all_employees_name_like(VARCHAR) OWNER TO postgres;

CREATE FUNCTION create_course(_name CHARACTER VARYING, _coordinator EMAIL, _description TEXT) RETURNS INTEGER
	LANGUAGE plpgsql
AS
$$
DECLARE
	_coordinator_id INT;
BEGIN
	IF _name IS NULL THEN
		RETURN 1;
	END IF;
	SELECT e.id
	FROM employees e
			 JOIN users u ON e.id = u.id
	WHERE u.email = _coordinator
	INTO _coordinator_id;
	IF _coordinator_id IS NULL THEN
		RETURN 2;
	END IF;
	INSERT INTO courses (name, coordinator, description) VALUES (_name, _coordinator_id, _description);
	RETURN 0;
END;
$$;

COMMENT ON FUNCTION create_course(VARCHAR, EMAIL, TEXT) IS 'Tworzy nowy kurs. Zwraca: 0 gdy kurs został utworzony, 1 gdy nazwa była null, 2 gdy podany kierownik kursu nie isnieje(nie jest pracownikiem lub nie ma użytkownika z takim emailem';

ALTER FUNCTION create_course(VARCHAR, EMAIL, TEXT) OWNER TO postgres;

CREATE FUNCTION create_class(_course INTEGER, _coordinator EMAIL, _semester CHARACTER) RETURNS INTEGER
	LANGUAGE plpgsql
AS
$$
DECLARE
	_coordinator_id INT;
BEGIN
	IF NOT _semester SIMILAR TO '2[0-9]{3}[LZ]' THEN
		RETURN 1;
	END IF;
	IF NOT EXISTS(SELECT id FROM courses WHERE id = _course) THEN
		RETURN 2;
	END IF;
	SELECT e.id
	FROM employees e
			 JOIN users u ON e.id = u.id
	WHERE u.email = _coordinator
	INTO _coordinator_id;
	IF _coordinator_id IS NULL THEN
		RETURN 3;
	END IF;
	INSERT INTO classes (course, coordinator, semester) VALUES (_course, _coordinator_id, _semester);
	RETURN 0;
END;
$$;

ALTER FUNCTION create_class(INTEGER, EMAIL, CHAR) OWNER TO postgres;


