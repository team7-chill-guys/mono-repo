MSA 기반 물류 관리 시스템 "물류ON"

<img src="https://github.com/user-attachments/assets/5abb22ef-cf31-4bf9-942e-c216eb58b56b" alt="서비스 소개" width="100%"/>


<br/>
<br/>

# 1. About Project
- 프로젝트명: MSA 기반 국내 물류 관리 및 배송 시스템 개발
- 프로젝트 목표:
    - 기존 모놀리식 시스템의 한계를 극복하기 위해 MSA 구조 도입, 각 도메인을 독립적으로 설계
    - DDD 관점 적용하여 물류 시스템을 체계적으로 모델링 및 개발
    - 실제 운영 환경에서도 활용 가능한 구조를 갖춘 시스템 구현
- 진행 기간: 2025년 4월 11일 ~ 4월 25일 (총 15일간)

<br/>
<br/>

# 2. Team7 Chill Guys
| 김소진 | 박소해 | 오혜민 | 조규성 | 주소연 |
|:------:|:------:|:------:|:------:|:------:|
| <img src="https://avatars.githubusercontent.com/sojinnuna" alt="김소진" width="150"> | <img src="https://avatars.githubusercontent.com/S2gamzaS2" alt="박소해" width="150"> | <img src="https://avatars.githubusercontent.com/u/170385509?v=4" alt="오혜민" width="150"> | <img src="https://avatars.githubusercontent.com/u/113866973?v=4" alt="조규성" width="150"> | <img src="https://avatars.githubusercontent.com/u/113866973?v=4" alt="주소연" width="150"> |
| Lead | BE | BE | BE | BE |
| [GitHub](https://github.com/sojinnuna) | [GitHub](https://github.com/S2gamzaS2) | [GitHub](https://github.com/sojinnuna) | [GitHub](https://github.com/kylim99) |

<br/>
<br/>

# 3. Tasks & Responsibilities (작업 및 역할 분담)
- **권한별 회원 관리**:
  - 권한별 회원가입을 진행합니다
  - 권한은 USER(고객), MASTER(관리자), OWNER(가게주인), MANAGER(가게매니저)로 분류되어 있습니다
  - 로그인이 진행되고, 필터 단에서 유저의 권한을 확인하여 토큰을 발급합니다.
  - 발급된 토큰을 기반으로 권한별 API를 처리합니다.
  - Security Config에서 권한을 처리합니다.

<br/>
<br/>

# 4. ERD
erdcloud : [https://www.erdcloud.com/d/sT8WDsAXXqfbB9Gay](https://www.erdcloud.com/d/JCRKQRxseMZY28368)
<br/>
<br/>


<br/>
<br/>


# 5. Technology Stack (기술 스택)

## 5.1 BackEnd
|  |  |  |
|-----------------|-----------------|-----------------|
| SpringBoot    |  <img src="https://img.shields.io/badge/SpringBoot-6DB33F?style=for-the-badge&logo=SpringBoot&logoColor=white" alt="SpringBoot" width="200"> | 3.4.3    |
| Java    |  <img src="https://img.shields.io/badge/Java-007396?style=for-the-badge&logo=Java&logoColor=white" alt="Java" width="200" > | 17 |
| Spring Data JPA    |  <img src="https://img.shields.io/badge/Spring Data JPA-6DB33F?style=for-the-badge&logo=SpringDataJPA&logoColor=white" alt="JPA" width="200" >    | 5.0.0  |
| QueryDSL    |  <img src="https://img.shields.io/badge/QueryDSL-0769AD?style=for-the-badge&logo=QueryDSL&logoColor=white" alt="QueryDSL" alt="QueryDSL" width="200" >    | 1.11.12    |
| Spring Security |  <img src="https://img.shields.io/badge/Spring Security-6DB33F?style=for-the-badge&logo=SpringSecurity&logoColor=white" alt="QueryDSL" alt="QueryDSL" width="200">    | 3.4.2    |

<br/>
<br/>
