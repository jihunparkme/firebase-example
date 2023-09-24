# Firebase

구글에서 제공하는 모바일 앱/웹을 위한 플랫폼인 `Firebase` 을 알아보려고 한다.

AWS 같은 클라우드 컴퓨팅 플랫폼으로 기대(?)했었는데.. 알고 보니 백엔드의 역할을 대신해 주는 플랫폼으로 보인다.😥

.

인증, 데이터베이스, 스토리지, 푸시 알림 등의 기능을 제공하여 백엔드 개발 없이 프론트 개발만으로도 빠른 앱/웹 개발이 필요한 사람들에게는 유용할 것 같다.

백엔드 개발을 해야 하는데.. 필요가 없다니..🥲

백엔드 개발이 필요 없는 플랫폼인만큼.. Spring-boot 프로젝트를 firebase 에 배포할 수는 없어 보이고 필요하다면 별도 서버에 배포가 필요해 보인다.

.

Firebase 를 알아보는 김에 Spring-boot 와 연동까지는 해보자.

Spring-boot 와 Firebase 연동을 위해 작성된 자세한 코드는 [Github](https://github.com/jihunparkme/firebase-example) 에서 확인해 볼 수 있다.

> 파이어베이스(Firebase)는 2011년 파이어베이스(Firebase, Inc)사가 개발하고 2014년 구글에 인수된 모바일 및 웹 애플리케이션 개발 플랫폼이다.

ref. [https://ko.wikipedia.org/wiki/firebase](https://ko.wikipedia.org/wiki/firebase)

.

> Firebase 는 구글에서 제공하는 모바일 앱/웹을 위한 플랫폼이다. 
>
> Firebase는 인증, 데이터베이스, 스토리지, 푸시 알림, 호스팅, Function 등 여러 기능을 제공하기 때문에 개발자가 직접 일일이 기능을 개발할 필요가 없다. 
> 
> 백엔드 기능을 클라우드 서비스 형태로 제공하기 때문에 간단한 조작으로 서버리스 애플리케이션 개발이 가능하며, 서버를 구입할 필요도 없다.

ref. [https://velog.io/@khy226/Firestore-%EC%95%8C%EC%95%84%EB%B3%B4%EA%B8%B0](https://velog.io/@khy226/Firestore-%EC%95%8C%EC%95%84%EB%B3%B4%EA%B8%B0)

.

[Firebase](https://firebase.google.com/?hl=ko)

[Firebase Documentation](https://firebase.google.com/docs?hl=ko)


## Project

### 프로젝트 추가

[Firebase Console](https://console.firebase.google.com/) 에서 Firebase 프로젝트를 추가할 수 있다.

![Result](https://raw.githubusercontent.com/jihunparkme/firebase-example/main/img/1-generate-project.png)

### 비공개 키 생성

Spring-boot 와 연동을 위해 비공개 키 생성이 필요하다.

프로젝트 설정으로 먼저 이동해 보자.

![Result](https://raw.githubusercontent.com/jihunparkme/firebase-example/main/img/2-project-setting.png)

.

프로젝트에 대한 여러 설정이 나오는데, 서비스 계정 탭을 보면 새 비공개 키 생성 버튼이 있다.

![Result](https://raw.githubusercontent.com/jihunparkme/firebase-example/main/img/3-generate-private-key.png)

여기서 생성된 .json 파일의 비공개 키를 Spring-boot 프로젝트의 resources 디렉토리 경로에 넣어주자.

### Cloud Firestore

> Firestore 는 firebase 에서 지원하는 NoSQL 데이터베이스 서비스로, 실시간 리스너를 통해 사용자와 기기간 데이터의 실시간 동기화가 가능하다. 
> 
> 또한, Cloud Firestore 는 앱에서 많이 사용되는 데이터를 캐시하기 때문에 기기가 오프라인 상태가 되더라도 앱에서 데이터를 쓰고 읽고 수신 대기하고 쿼리할 수 있다.

ref. [https://velog.io/@khy226/Firestore-%EC%95%8C%EC%95%84%EB%B3%B4%EA%B8%B0](https://velog.io/@khy226/Firestore-%EC%95%8C%EC%95%84%EB%B3%B4%EA%B8%B0)

.

데이터베이스 생성도 간단하다.

[Cloud Firestore](https://firebase.google.com/docs/firestore?hl=ko) 는 NoSQL 클라우드 데이터베이스를 사용한다.

데이터 추가를 위해 데이터베이스 생성 후 컬렉션, 문서, 필드를 추가해 주면 된다.

NoSQL 의 컬렉션, 문서, 필드를 RDBMS 계층과 간단하게 비교해 보면 아래와 같다.

- Collections - Tables
- Documents - Rows
- Fields - Columns

![Result](https://raw.githubusercontent.com/jihunparkme/firebase-example/main/img/4-cloud-firestore.png)

# Spring Boot

이제 본격적으로 Firebase 와 Spring-boot 를 연동해 보자.

- spring-boot: '3.1.4'
- java: '17'

## Firebase 연동

**Dependency**

[Firebase MVN Repository](https://mvnrepository.com/artifact/com.google.firebase/firebase-admin)

```groovy
implementation 'com.google.firebase:firebase-admin:9.2.0'
```

FileInputStream 을 통해 비공개 키 파일을 읽고, firebase 개인 프로젝트 정보를 통해 FirebaseApp 을 초기화하여 연동을 할 수 있다.

```java
@Configuration
public class FirebaseConfig {

    @PostConstruct
    public void init() {
        try {
            FileInputStream serviceAccount = new FileInputStream("src/main/resources/config/key/serviceAccountKey.json");
            FirebaseOptions.Builder optionBuilder = FirebaseOptions.builder();
            FirebaseOptions options = optionBuilder
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();
            FirebaseApp.initializeApp(options);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```

프론트단에 직접 데이터를 조작할 수도 있겠지만, 서버에서 직접 조작을 해보자.

### 데이터(Document) 추가

```java
public void addUser(User user) {
    Query query = FIRE_STORE.collection(COLLECTION_NAME).whereEqualTo("email", user.getEmail());
    ApiFuture<QuerySnapshot> querySnapshot = query.get();
    
    DocumentReference document = null;
    if (isNotExistEmail(querySnapshot)) {
        document = FIRE_STORE.collection(COLLECTION_NAME).document();
        user.setId(document.getId());
        document.set(user);
        log.info("새로운 문서가 추가되었습니다. document ID: {}", document.getId());
    } else {
        throw new RuntimeException("이미 가입된 이메일입니다.");
    }
}

...

@Test
void addUser() throws Exception {
    User user = User.builder()
            .name("bbb")
            .email("bbb@gmail.com")
            .create_dt(Timestamp.now())
            .build();
    
    userRepository.addUser(user);
}
```

### 전체 데이터(Document) 전체 조회

```java
public List<User> getAllUsers() throws ExecutionException, InterruptedException {
    List<User> list = new ArrayList<>();
    ApiFuture<QuerySnapshot> future = FIRE_STORE.collection(COLLECTION_NAME).get();
    List<QueryDocumentSnapshot> documents = future.get().getDocuments();
    for (QueryDocumentSnapshot document : documents) {
        list.add(document.toObject(User.class));
    }
    return list;
}

...

@Test
void getAllUsers() throws Exception {
    List<User> users = userRepository.getAllUsers();
    users.forEach(user -> System.out.println("user = " + user));
}
```

### 특정 데이터(Document) 조회

```java
public Optional<User> findUserByEmail(String email) {
    Query query = FIRE_STORE.collection(COLLECTION_NAME).whereEqualTo("email", email);
    ApiFuture<QuerySnapshot> future = query.get();
    try {
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        if (!documents.isEmpty()) {
            return Optional.of(documents.get(0).toObject(User.class));
        }
        return Optional.empty();
    } catch (Exception e) {
        throw new RuntimeException("문서 조회를 실패하였습니다.");
    }
}

...

@Test
void findUserByEmail() throws Exception {
    String email = "park@gmail.com";

    Optional<User> userOptional = userRepository.findUserByEmail(email);
    User user = userOptional.get();
    
    assertEquals("Park", user.getName());
    assertEquals(email, user.getEmail());
}
```

### 데이터(Document) 수정

```java
public void editUser(User user) {
    Query query = FIRE_STORE.collection(COLLECTION_NAME).whereEqualTo("email", user.getEmail());
    ApiFuture<QuerySnapshot> querySnapshot = query.get();

    DocumentReference document = null;
    if (isExistEmail(querySnapshot)) {
        document = FIRE_STORE.collection(COLLECTION_NAME).document(user.getId());
        document.update("name", user.getName());
        document.update("update_dt", Timestamp.now());
        log.info("문서가 수정되었습니다. document ID: {}", document.getId());
    } else {
        throw new RuntimeException("해당 이메일로 계정이 존재하지 않습니다.");
    }
}

...

@Test
void editUser() throws Exception {
    User user = User.builder()
            .id("yXMhDPpulEWGqO3ERzEU")
            .name("Aaron1")
            .email("aaron@gmail.com")
            .create_dt(Timestamp.now())
            .build();
    
    userRepository.editUser(user);
}
```

### 데이터(Document) 삭제

```java
public void removeUserByEmail(String email) {
    Query query = FIRE_STORE.collection(COLLECTION_NAME).whereEqualTo("email", email);
    ApiFuture<QuerySnapshot> future = query.get();
    try {
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        if (!documents.isEmpty()) {
            User user = documents.get(0).toObject(User.class);
            FIRE_STORE.collection(COLLECTION_NAME).document(user.getId()).delete();
            return;
        }
        throw new RuntimeException("해당 이메일로 계정이 존재하지 않습니다.");
    } catch (Exception e) {
        throw new RuntimeException("문서 조회를 실패하였습니다.");
    }
}

...

@Test
void removeUserByEmail() throws Exception {
    String email = "bbb@gmail.com";
    userRepository.removeUserByEmail(email);
}
```

## Reference

- [파이어베이스로 호스팅하기](https://spiralmoon.tistory.com/entry/Firebase-%ED%8C%8C%EC%9D%B4%EC%96%B4%EB%B2%A0%EC%9D%B4%EC%8A%A4%EB%A1%9C-%ED%98%B8%EC%8A%A4%ED%8C%85%ED%95%98%EA%B8%B0)
- https://hayeondev.gatsbyjs.io/220125-firestore-react-chat-app/
