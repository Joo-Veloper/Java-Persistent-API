package jpql;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {

            Team team = new Team();
            team.setName("teamA");
            em.persist(team);

            Member member = new Member();
            member.setUsername("관리자");
            member.setAge(10);
            member.setType(MemberType.ADMIN);

            member.setTeam(team);

            em.flush();
            em.clear();

            // 기본 CASE 식
//            String query =
//                    "select " +
//                    "case when m.age <= 10 then '학생 요금' " + // 공백 추가
//                    "when m.age >= 60 then '경로 요금' " + // 공백 추가
//                    "else '일반요금' " +
//                    "end " + // CASE 문 끝을 명시
//                    "from Member m";
            // Coalesce
//            String query = "select coalesce(m.username, '이름 없는 회원') as username " + "from  Member m ";
            //NULLIF
            String query = "select nullif(m.username, '관리자') as username " + "from  Member m ";

            List<String> result = em.createQuery(query, String.class)
                    .getResultList();
            for (String s : result) {
                System.out.println("s = " + s);
            }

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
        emf.close();
    }
}

//-------------------------------------------------------------------------------------------------------------------------------------------

//public class JpaMain {
//
//    public static void main(String[] args) {
//        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
//        EntityManager em = emf.createEntityManager();
//        EntityTransaction tx = em.getTransaction();
//        tx.begin();
//        try {
//
//            Team team = new Team();
//            team.setName("teamA");
//            em.persist(team);
//
//            Member member = new Member();
//            member.setUsername("teamA");
//            member.setAge(10);
//            member.setType(MemberType.ADMIN);
//
//            member.setTeam(team);
//
//            em.flush();
//            em.clear();
//
//            // JPQL 타입 표현
//            String query = "select m.username, 'HELLO', true From Member m " +
//                    "WHERE m.type = :userType";
//
//            List<Object[]> result = em.createQuery(query)
//                    .setParameter("userType", MemberType.ADMIN)
//                    .getResultList();
//
//            for(Object[] objects : result) {
//                System.out.println("objects = " + objects[0]);
//                System.out.println("objects = " + objects[1]);
//                System.out.println("objects = " + objects[2]);
//            }
//
//            tx.commit();
//        } catch (Exception e) {
//            tx.rollback();
//            e.printStackTrace();
//        } finally {
//            em.close();
//        }
//        emf.close();
//    }
//}

//-------------------------------------------------------------------------------------------------------------------------------------------

//public class JpaMain {
//
//    public static void main(String[] args) {
//        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
//        EntityManager em = emf.createEntityManager();
//        EntityTransaction tx = em.getTransaction();
//        tx.begin();
//        try {
//
//            Team team = new Team();
//            team.setName("teamA");
//            em.persist(team);
//
//            Member member = new Member();
//            member.setUsername("member1");
//            member.setAge(10);
//
//            member.setTeam(team);
//
//            em.flush();
//            em.clear();
//
//            // JPA 서브 쿼리
//            String query = "select (select avg(m1.age)from Member m1)as avgAge from Member m join Team t on m.username = t.name";
//            List<Member> result = em.createQuery(query, Member.class)
//                    .getResultList();
//
//            System.out.println("result =" + result.size());
//
//
//            tx.commit();
//        } catch (Exception e) {
//            tx.rollback();
//            e.printStackTrace();
//        } finally {
//            em.close();
//        }
//        emf.close();
//    }
//}

//-------------------------------------------------------------------------------------------------------------------------------------------

//public class JpaMain {
//
//    public static void main(String[] args) {
//        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
//        EntityManager em = emf.createEntityManager();
//        EntityTransaction tx = em.getTransaction();
//        tx.begin();
//        try {
//
//            Team team = new Team();
//            team.setName("teamA");
//            em.persist(team);
//
//            Member member = new Member();
//            member.setUsername("member1");
//            member.setAge(10);
//
//            member.setTeam(team);
//
//
//            em.flush();
//            em.clear();
//            //------------------------ Join ------------------------
//            // left join
////            String query = "select m from Member m left join m.team t";
////            List<Member> result = em.createQuery(query, Member.class)
////                    .getResultList();
//
//            //setter join
////            String query = "select m from Member m, Team t where m.username = t.name";
////            List<Member> result = em.createQuery(query, Member.class)
////                    .getResultList();
//            // 조인 대상 필터링
////            String query = "select m from Member m left join m.team t on t.name = 'teamA'";
////            List<Member> result = em.createQuery(query, Member.class)
////                    .getResultList();
//
//            // 연관관계 없는 엔티티 외부 조인
//            String query = "select m from Member m left join Team t on m.username = t.name";
//            List<Member> result = em.createQuery(query, Member.class)
//                    .getResultList();
//
//            System.out.println("result =" + result.size());
//
//            tx.commit();
//        } catch (Exception e) {
//            tx.rollback();
//            e.printStackTrace();
//        } finally {
//            em.close();
//        }
//        emf.close();
//    }
//}

//-------------------------------------------------------------------------------------------------------------------------------------------

//public class JpaMain {
//
//    public static void main(String[] args) {
//        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
//        EntityManager em = emf.createEntityManager();
//        EntityTransaction tx = em.getTransaction();
//        tx.begin();
//        try {
//            for (int i = 0; i < 100; i++) {
//                Member member = new Member();
//                member.setUsername("member" + i);
//                member.setAge(i);
//                em.persist(member);
//            }
//
//            em.flush();
//            em.clear();
//            // ------------------------ 페이징 ------------------------
//
//            List<Member> result = em.createQuery("select m from Member m order by m.age desc ", Member.class)
//                    .setFirstResult(1)
//                    .setMaxResults(10)
//                    .getResultList();
//
//            System.out.println("result.size = " + result.size());
//            for (Member member1 : result) {
//                System.out.println("member1 = " + member1);
//            }
//
//
//            tx.commit();
//        } catch (Exception e) {
//            tx.rollback();
//            e.printStackTrace();
//        } finally {
//            em.close();
//        }
//        emf.close();
//    }
//}

//-------------------------------------------------------------------------------------------------------------------------------------------

//public class JpaMain {
//
//    public static void main(String[] args) {
//        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
//        EntityManager em = emf.createEntityManager();
//        EntityTransaction tx = em.getTransaction();
//        tx.begin();
//        try {
//            Member member = new Member();
//            member.setUsername("member1");
//            em.persist(member);
//            member.setAge(10);
//            em.persist(member);
//
////            //TypeQuery = 반환 값이 명확할 때 사용
////            // Query = 반환 타입이 명확하지 않을 때 사용
////            TypedQuery<Member> query1 = em.createQuery("select m from Member m", Member.class);
////            // 타입 정보를 명기할 수 없을때
////            TypedQuery<String> query2 = em.createQuery("select m.username from Member m", String.class);
////            //타입 정보를 받을 수 없을때
////            Query query3 = em.createQuery("select m.username from Member m");
//
//            //결과 조회 API
//            // 컬랙션이 반환 될꺼야!
////            TypedQuery<Member> query = em.createQuery("select m from Member m", Member.class);
////            List<Member> resultList = query.getResultList();
////            for (Member member1 : resultList) {
////                System.out.println("member1 = " + member1);
////            }
//            // Query를 날려서 값이 무조건 하나
////            TypedQuery<Member> query = em.createQuery("select m from Member m where  m.id = 10", Member.class);
////            // query.getResultList():결과가 하나 이상일 때
////            // query.getSingleResult(): 결과가 정확히 하나 있을때만 사용!, 단일 객체 반환
////            Member result = query.getSingleResult();
////            //Spring DATA JPA -> 결과 없으면 null 이나 optional 반환!
////            System.out.println("result = " + result);
//
//
////            //파라미터 바인딩
////            //이름 기준
////            Member result = em.createQuery("select m from Member m where  m.username = ?1", Member.class)
////                    .setParameter("1", "member1")
////                    .getSingleResult();
////            System.out.println("result = " + result.getUsername());
////
////            //위치 기반 -> 거의 사용 xx
//
//            //영속성 컨텍스트 깔끔히 비움
//            em.flush();
//            em.clear();
//
////            ------------------------프로젝션------------------------
//            // 엔티티 프로젝션
////            List<Member> result = em.createQuery("select m from Member m", Member.class)
////                            .getResultList();
////            Member findMember = result.get(0);
////            findMember.setAge(20);
//
//            // 엔티티 프로젝션
////            List<Team> result = em.createQuery("select t from Member m join m.team t", Team.class)
////                    .getResultList();
//
//            // 임베디드 타입 프로젝션
////            em.createQuery("select o.address from Order o", Address.class)
////                    .getResultList();
////
//            // 스칼라 타입 프로젝션 (원하는것 을 막 가져옴)
//
//            // 1번쨰 방법
////            List resultList = em.createQuery("select distinct m.username, m.age from Member m")
////                    .getResultList();
////            Object o = resultList.get(0);
////            Object[] result = (Object[]) o;
////            System.out.println("username = " + result[0]);
////            System.out.println("age = " + result[1]);
//
//            // 2번쨰 방법
////            List<Object[] > resultList = em.createQuery("select m.username, m.age from Member m")
////                    .getResultList();
////            Object[] result = resultList.get(0);
////            System.out.println("username = " + result[0]);
////            System.out.println("age = " + result[1]);
////
//            // 3번째 방법
//            // 패키지가 문자이기에 한계는 있음!! -> jpql.MemberDTO
////            List<MemberDTO> result = em.createQuery("select new jpql.MemberDTO(m.username, m.age) from Member m",MemberDTO.class)
////                    .getResultList();
////            MemberDTO memberDTO = result.get(0);
////            System.out.println("memberDTO = " + memberDTO.getUsername());
////            System.out.println("memberDTO = " + memberDTO.getAge());
//
//
//            tx.commit();
//        } catch (Exception e) {
//            tx.rollback();
//            e.printStackTrace();
//        } finally {
//            em.close();
//        }
//        emf.close();
//    }
//}

//JPQL 문법!
// SELECT_절
// FROM_절
// [WHERE_절]
// [GROUPBY_절]
// [HAVING_절]
// [ORDERBY_절]

// update문 = update_절[where_절]
// delete문 = delete_절[where_절]

//select m from Member as m wher m.age>18
// member와 age는 대소문자를 구분해야함!!
// JPQL 키워드 대소문자 구분 XX
// 엔티티 이름 사용 -> @Entity(name = "Member")


