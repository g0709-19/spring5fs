package spring;

/*
 * The serializable class WrongIdPasswordException does not declare a static final serialVersionUID
 * field of type long 오류 발생 이유:
 */
public class WrongIdPasswordException extends RuntimeException {

}
