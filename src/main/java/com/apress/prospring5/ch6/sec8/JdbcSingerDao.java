package com.apress.prospring5.ch6.sec8;

import com.apress.prospring5.ch6.dao.SingerDao;
import com.apress.prospring5.ch6.entities.Singer;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;

public class JdbcSingerDao implements SingerDao, InitializingBean {

    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;

    /**
     * MySQLErrorCodesTranslator : 새로 만든 SQLExceptionTranslator 구현체.
     *
     * 에러 코드가 -12345 이면 SQL 예외 발생 시 커스텀 예외 변환 로직이 동작한다.
     */
    public void setDataSource(DataSource dataSource){
        this.dataSource = dataSource;
        JdbcTemplate jdbcTemplate = new JdbcTemplate(); //jdbcTemplate를 초기화 하는 코드.(예제 6-31)
        jdbcTemplate.setDataSource(dataSource);
        MySQLErrorCodesTranslator errorCodesTranslator = new MySQLErrorCodesTranslator();
        errorCodesTranslator.setDataSource(dataSource);
        jdbcTemplate.setExceptionTranslator(errorCodesTranslator);
        this.jdbcTemplate = jdbcTemplate;

    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (jdbcTemplate == null) {
            throw new BeanCreationException("Null JdbcTemplate on SingerDao");
        }
    }

    @Override
    public List<Singer> findAll() {
        return null;
    }

    @Override
    public List<Singer> findByFirstName(String firstName) {
        return null;
    }

    /**
     * 6.9.2 JdbcTemplate를 사용해 단일 값 조회하기.
     * 예제 6-33. findNameById 메서드 추가.
     * 첫번째인자 - SQL 구문
     * 두번쨰인자 - 파라미터에 바인딩할 값을 Object 배열 형태로 지정
     * 세번쟤인자 - 반환 값의 타입 지정.
     */
    @Override
    public String findNameById(Long id) {
        return jdbcTemplate.queryForObject(
          "select first_name || ' ' || last_name from singer where id = ?",
          new Object[]{id}, String.class);
    }

    @Override
    public String findLastNameById(Long id) {
        return null;
    }

    @Override
    public String findFirstNameById(Long id) {
        return null;
    }

    @Override
    public void insert(Singer singer) {
        throw new NotImplementedException("insert");
    }

    @Override
    public void update(Singer singer) {

    }

    @Override
    public void delete(Long singerId) {

    }

    @Override
    public List<Singer> findAllWithAlbums() {
        return null;
    }

    @Override
    public void insertWithAlbum(Singer singer) {

    }
}
