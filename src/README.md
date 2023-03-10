학습 목표  
트랜잭션(Transaction)이 무엇인지 이해할 수 있다.  
트랜잭션(Transaction) 경계가 무엇인지 이해할 수 있다.  
Spring에서 지원하는 트랜잭션 방식을 이해할 수 있다.  
샘플 애플리케이션에 트랜잭션을 적용할 수 있다.  
  
  
무조건 여러 개의 작업을 그룹으로 묶는다고해서 트랜잭션이라고 부를 수 있는게 아니라  
물리적으로는 여러 개의 작업이지만 논리적으로는 마치 하나의 작업으로 인식해서  
전부 성공하든가 전부 실패하든가(All or Nothing)의 둘 중 하나로만 처리되어야 트랜잭션의 의미를 가집니다.  
이러한 All or Nothing이라는 트랜잭션 처리 방식은 애플리케이션에서 사용하는 데이터의 무결성을 보장하는 핵심적인 역할을 합니다.  

트랜잭션 커밋(commit)과 롤백(rollback)  
  
커밋(commit)  
커밋(commit)은 모든 작업을 최종적으로 데이터베이스에 반영하는 명령어로써  commit 명령을 수행하면 변경된 내용이 데이터베이스에 영구적으로 저장됩니다.  
만약 commit 명령을 수행하지 않으면 작업의 결과가 데이터베이스에 최종적으로 반영되지 않습니다.  
commit 명령을 수행하면, 하나의 트랜젝션 과정은 종료하게됩니다.  
롤백(rollback)  
롤백(rollback)은 작업 중 문제가 발생했을 때, 트랜잭션 내에서 수행된 작업들을 취소합니다.  
따라서 트랜잭션 시작 이 전의 상태로 되돌아갑니다.  

commit 직전에 rollback 명령을 먼저 수행하면 데이터는 테이블에 저장되지 않음.  

트랜잭션의 진정한 의미  
트랜잭션의 의미를 이해하기 위해 우리가 데이터베이스에서 작업을 처리하는 상황을 예로 들었지만  
트랜잭션은 사실 데이터베이스에만 한정해서 사용하는 의미는 아닙니다.  

예를 들어서 어떤 데이터를 로컬 데이터베이스에도 저장하고, 그 결과를 푸시 알림(push notification)으로 클라이언트에게 전송하는 기능이 있다면  
데이터베이스 저장과 푸시 알림 전송이라는 두 개의 작업이 하나의 트랜잭션으로 묶여져서  
둘 중에 하나라도 실패할 경우 롤백(rollback)이 되어야 할 수도 있습니다.(정책적으로 이 두 개의 작업을 별개로 보는 경우도 있습니다.)  

이처럼 전혀 다른 타입의 리소스(데이터베이스, 파일, 메시지 등)를 하나의 작업 단위로 묶어서 처리해야되는 상황에서는  
어떤식으로 트랜잭션을 적용하면 좋을지에 대해서 꼭 고민해보기.  


핵심 포인트  
트랜잭션은 여러개의 작업들을 하나의 그룹으로 묶어서 처리하는 처리 단위를 의미한다.  
  
ACID 원칙  
원자성(Atomicity)  
트랜잭션에서의 원자성(Atomicity)이란 작업을 더이상 쪼갤 수 없음을 의미한다.  
따라서 논리적으로 하나의 작업으로 인식해서 둘 다 성공하든 둘 다 실패하든가(All or Nothing)의 둘 중 하나로만 처리되는 것이 보장된다.  
일관성(Consistency)  
일관성(Consistency)은 트랜잭션이 에러없이 성공적으로 종료될 경우, 비즈니스 로직에서 의도하는대로 일관성있게 저장되거나 변경되는 것을 의미한다.  
고립성(Isolation)  
고립성(Isolation)은 여러 개의 트랜잭션이 실행될 경우 각각 독립적으로 실행이 되어야 함을 의미한다.  
지속성(Durability)  
지속성(Durability)은 여러분들이 잘 알고있다시피 데이터베이스가 종료되어도 데이터는 물리적인 저장소에 저장되어 지속적으로 유지되어야 한다는 의미한다.  
  
커밋(commit)  
커밋(commit)은 모든 작업을 최종적으로 데이터베이스에 반영하는 명령어로써  commit 명령을 수행하면 변경된 내용이 데이터베이스에 영구적으로 저장된다.  
만약 commit 명령을 수행하지 않으면 작업의 결과가 데이터베이스에 최종적으로 반영되지 않는다.  
commit 명령을 수행하면, 하나의 트랜젝션 과정은 종료하게된다.  
  
롤백(rollback)  
롤백(rollback)은 작업 중 문제가 발생했을 때, 트랜잭션 내에서 수행된 작업들을 취소한다.  
따라서 트랜잭션 시작 이 전의 상태로 되돌아간다.  
JPA 기술을 사용한 데이터베이스와의 인터랙션은 내부적으로는 JDBC API를 통해서 이루어진다.  
  
  
학습 목표  
선언형 트랜잭션 방식의 의미를 이해할 수 있다.  
선언형 트랜잭션 방식을 샘플 애플리케이션에 적용할 수 있다.  
로컬 트랜잭션과 분산 트랜잭션의 의미를 이해할 수 있다.  
분산 트랜잭션을 샘플 애플리케이션에 적용할 수 있다.  
우리는 선언형 트랜잭션 방식을 사용하여 공부할것.  

우리는 Spring Boot을 사용하고 있기 때문에 트랜잭션 관련 설정은 Spring Boot이 내부적으로 알아서 해줍니다.  
그렇기에 트랜잭션을 위해 PlatformTransactionManager, Datasource 객체를 뽑아내서 사용하지 않아도 된다.(원래는 필요하지만)  
Spring에서 트랜잭션을 적용하는 가장 간단한 방법은 @Transactional이라는 애너테이션을 트랜잭션이 필요한 영역에 추가해 주는 것.  

클래스 레벨에 @Transactional 적용 (멤버서비스)  
1.로그 보기 위해 yml 수정 2.post로 로그 본 후 3.createMember 매서드 안에 롤백 보기 위해 로직 추가.(결국 저장 x)  
롤백 보기 위한 예외는 언체크 예외이고, 언체크 예외는 RuntimeException 클래스를 상속받은 예외 클래스들이다.  
체크 예외는 @Transactional 애너테이션만 추가해서는 rollback이 되지 않는다.
  
메서드 레벨에 @Transactional 적용 (멤버서비스 파인드멤버)  
findeMember()와 같은 조회 메서드에@Transactional(readOnly = true)로 설정해도 commit 절차를 진행하기는 함.  
JPA에서 commit이 호출되면 영속성 컨텍스트가 flush 됨.  
그런데 @Transactional(readOnly = true) 로 설정하면 JPA 내부적으로 영속성 컨텍스트를 flush하지 않음.  
그리고 읽기 전용 트랜잭션일 경우, 변경 감지를 위한 스냅샷 생성도 진행하지 않음.  
flush 처리를 하지 않고, 스냅샷도 생성하지 않으므로 불필요한 추가 동작을 줄일 수 있음.  
즉, 조회 메서드에는 readonly 속성을 true로 지정해서 JPA가 자체적으로 성능 최적화 과정을 거치도록 하는것이 좋음.  
(flush() 는 우리가 DB Editor 에서 SQL 을 작성하는것이라고 할 수 있으며, 트랜잭션 commit()은 말 그대로 COMMIT 을 수행하는것, DB에 밀어내는것.)  


클래스 레벨과 메서드 레벨의 트랜잭션 적용 순서  
클래스 레벨과 메서드 레벨에 @Transactional 애너테이션을 추가할 때 트랜잭션은 다음과 같이 적용됩니다.  
  
클래스 레벨에만 @Transactional이 적용된 경우  
클래스 레벨의 @Transactional 애너테이션이 메서드에 일괄 적용됩니다.  
  
클래스 레벨과 메서드 레벨에 함께 적용된 경우
메서드 레벨의 @Transactional 애너테이션이 적용됩니다.  
만약 메서드 레벨에 @Transactional 애너테이션이 적용되지 않았을 경우, 클래스 레벨의 @Transactional 애너테이션이 적용됩니다.  
  
  
  
  
트랜잭션을 하나로 묶어야 하는 경우가 존재.  
(예를 들어, 커피 주문 하면 스탬프도 하나 찍어주는 것과 같은 두가지 행동.)  
그러한 경우를 OrderService 자체와(createOrder), MemberService(updateMember) 의 코드를 바꿔준 후  
  
postOrder() 호출 전에 먼저 회원 정보를 등록하고, 다음으로 커피 정보를 등록한 후에 등록된 회원과 커피 정보로 주문 테스트를 진행.  
실행 결과를 보면 OrderService에서 강제로 발생 시킨 예외로 인해 rollback이 진행되는 것을 확인.
결과적으로 두 작업(멤버, 커피등록)이 OrderService에서 시작된 하나의 트랜잭션에 묶여있기 때문에 두 개의 작업은 모두 rollback 처리  
(어려우면 [Spring MVC] 트랜잭션(Transaction) 들어가서 보기)  


트랜잭션 전파(Transaction Propagation)  
트랜잭션 전파란 트랜잭션의 경계에서 진행 중인 트랜잭션이 존재할 때 또는 존재하지 않을 때, 어떻게 동작할 것인지 결정하는 방식을 의미합니다.  
  
트랜잭션 전파는 propagation 애트리뷰트를 통해서 설정할 수 있으며, 대표적으로 아래와 같은 propagation 유형을 사용할 수 있습니다.  
  
Propagation.REQUIRED  
우리가 앞에서 @Transactional 애너테이션의 propagation 애트리뷰트에 지정한 Propagation.REQUIRED 는 일반적으로 가장 많이 사용되는 propagation 유형의 디폴트 값입니다.  
진행 중인 트랜잭션이 없으면 새로 시작하고, 진행 중인 트랜잭션이 있으면 해당 트랜잭션에 참여합니다.  
  
Propagation.REQUIRES_NEW  
이미 진행중인 트랜잭션과 무관하게 새로운 트랜잭션이 시작됩니다. 기존에 진행중이던 트랜잭션은 새로 시작된 트랜잭션이 종료할 때까지 중지됩니다.  
  
Propagation.MANDATORY  
Propagation.REQUIRED는 진행 중인 트랜잭션이 없으면 새로운 트랜잭션이 시작되는 반면, Propagation.MANDATORY는 진행 중인 트랜잭션이 없으면 예외를 발생시킵니다.  
  
Propagation.NOT_SUPPORTED  
트랜잭션을 필요로 하지 않음을 의미합니다. 진행 중인 트랜잭션이 있으면 메서드 실행이 종료될 때 까지 진행중인 트랜잭션은 중지되며, 메서드 실행이 종료되면 트랜잭션을 계속 진행합니다.  
  
Propagation.NEVER  
트랜잭션을 필요로 하지 않음을 의미하며, 진행 중인 트랜잭션이 존재할 경우에는 예외를 발생시킵니다.  
  
이처럼 Spring에서는 다양한 Propagation 유형을 지원하지만 작업별로 트랜잭션을 새로 생성해야 한다거나 특정 작업에는 트랜잭션을 적용하지 않는 등의 경우가 아니라면 @Transactional 애너테이션만 추가해도 무방하다고 생각합니다.  




트랜잭션 격리 레벨(Isolation Level)  
  
ACID 원칙에서 살펴보았다시피 트랜잭션은 다른 트랜잭션에 영향을 주지 않고, 독립적으로 실행되어야 하는 격리성이 보장되어야 하는데 Spring은 이러한 격리성을 조정할 수 있는 옵션을 @Transactional 애너테이션의 isolation 애트리뷰트를 통해 제공하고 있습니다.  
  
Isolation.DEFAULT  
데이터베이스에서 제공하는 기본 값입니다.  
  
Isolation.READ_UNCOMMITTED  
다른 트랜잭션에서 커밋하지 않은 데이터를 읽는 것을 허용합니다.  
  
Isolation.READ_COMMITTED  
다른 트랜잭션에 의해 커밋된 데이터를 읽는 것을 허용합니다.  
  
Isolation.REPEATABLE_READ  
트랜잭션 내에서 한 번 조회한 데이터를 반복해서 조회해도 같은 데이터가 조회되도록 합니다.  
  
Isolation.SERIALIZABLE  
동일한 데이터에 대해서 동시에 두 개 이상의 트랜잭션이 수행되지 못하도록 합니다.  
  
트랜잭션의 격리 레벨은 일반적으로 데이터베이스나 데이터소스에 설정된 격리 레벨을 따르는 것이 권장되므로, 이러한 격리 레벨이 있다라고 이해하고 넘어가면 될 것 같습니다.  
  
  
  
지금까지는 애너테이션을 사용했지만,  
AOP 방식의 트랜잭션 적용도 있다. 그 내용은 CoffeeService에 설명과 함께 달아놓음.  
(@Transactional 애너테이션 조차도 비즈니스 로직에 적용하지 않고, 트랜잭션을 적용하는 방법)  


핵심 포인트  
트랜잭션 관련 설정은 Spring Boot이 내부적으로 알아서 해주기 때문에 개발자가 직접적으로 트랜잭션 설정해줄 필요가 없다.  
Spring에서는 일반적으로 애너테이션 방식( @Transactional )의 트랜잭션과 AOP 방식의 트랜잭션 적용 방식을 사용한다.  
체크 예외(checked exeption)는 @Transactional 애너테이션만 추가해서는 rollback이 되지 않으며,  
@Transactional(rollbackFor = {SQLException.class, DataFormatException.class})와 같이  
해당 체크 예외를 직접 지정해주거나 언체크 예외(unchecked exception)로 감싸야 rollback 기능을 적용할 수 있다.  
트랜잭션 전파란 트랜잭션의 경계에서 진행 중인 트랜잭션이 존재할 때 또는 존재하지 않을 때, 어떻게 동작할 것인지 결정하는 방식을 의미한다.  
@Transactional 애너테이션의 isolation 애트리뷰트를 통해 트랜잭션 격리 레벨을 지정할 수 있다.  


JTA를 이용한 분산 트랜잭션 적용 (본 저장과 백업 저장 MySql으로.)  
하나의 PC에 물리적으로 MySQL 인스턴스 두 개를 실행시키는 것이 아니라 ‘create database’ 명령어로  
두 개의 데이터베이스를 생성해서 각각 별도의 데이터소스를 사용하는 구조라는 것을 기억  
백업용 회원 정보는 기존 회원 정보의 백업 데이터 역할을 하며, 분산 트랜잭션의 적용을 확인 하기 위한 학습용 임시 테이블이라고 생각  

같은 종류의 데이터베이스일 경우, 복제(Replication) 기능을 이용해서 데이터를 백업할 수 있습니다.  
다른 종류의 데이터베이스 간에 사용할 수 있는 방법은 애플리케이션의 스케쥴링 기능을 통해  
주기적으로 원본 데이터베이스의 데이터를 다른 데이터베이스로 백업하는 기능을 구현할 수 있으며,  
이런 기능들을 기본적으로 지원하는 Apache NiFi 같은 오픈 소스 기술을 사용할 수도 있습다.  
  
mysql 콘솔에서 기존의 커피 주문 정보를 위한 데이터베이스 정보, 백업 회원 정보를 위한 데이터베이스 정보를 저장.  
build.gradle에 의존 라이브러리 추가, XaCoffeeOrderConfig(커피 주문 위한 db), XaBackupConfig(백업용 회원 정보 db) 클래스 추가.  
(설명은 XaCoffeeOrderConfig 클래스에 다 달아놨음, 백업컨피크에서는 경로설정만 다르게.)  Config 패키지에서 생성해준 후  
MemberService 클래스의 createMember 로직에 백업에도 저장되도록 매서드 수정.   
  
event 만들어서 MemberService의 createMember 매서드 이메일 보내기 실패하면 롤백되도록 수정.  
(원래의 트랜잭션 생각해보면, createMember 에서 save와 emailsend는 비동기이기에 이메일 실패해도 롤백 안된다!)  