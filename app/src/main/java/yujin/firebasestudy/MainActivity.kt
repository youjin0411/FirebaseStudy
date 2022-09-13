package yujin.firebasestudy

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.Timestamp
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.io.File
import java.io.FileInputStream
import java.util.*

class MainActivity : AppCompatActivity() {
    val TAG = "mytag"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


//        val db = Firebase.firestore

        /// 스토리지 접근
        val storage = Firebase.storage
        // 스토리지 리퍼런스 객체 얻어오기 (이후 레퍼런스 객체를 이용하여 파일 업로드 다운로드 가능)
        var storageRef = storage.reference
        // 폴더가 없어도 모두 자동으로 생성하고 최종 폴더 내부에 파일이 저장됨
        val fileRef = storageRef.child("hello/world/hello.txt")
        // 업로드할 바이트 내용 구성
        var bytes = byteArrayOf(72,101,108,108,111)
        // 업로드 작업 진행 (실제 업로드가 진행되며 업로드 결과에 대한 콜백 함수 설정 위해서 반환받은 Task 객체에 접근 가능)
        val uploadTask = fileRef.putBytes(bytes)

        uploadTask.addOnFailureListener {
            Log.d("mytag", "업로드 실패")
        }.addOnSuccessListener {
            // 파일 업로드 성공 이후 메타데이터 객체에 접근하여 파일 관련 데이터 접근 가능
            val metadata = it.metadata
            // 파일 이름
            Log.d("mytag", metadata?.name.toString())
            // 파일 경로 + 파일 이름
            Log.d("mytag", metadata?.path.toString())
            // 파일 크기(바이트)
            Log.d("mytag", metadata?.sizeBytes.toString())
            // MIME 타입
            Log.d("mytag", metadata?.contentType.toString())
            // 생성 시간, 수정 시간
            Log.d("mytag", metadata?.creationTimeMillis.toString())
            Log.d("mytag", metadata?.updatedTimeMillis.toString())
        }

        val ref = storageRef.child("hello.txt")
// 내부 저장소에 파일 생성 후 저장하기 위해서 파일 객체 만들기
// (Device File Explorer의 data/data/디폴트패키지이름/files 폴더 내부에서 확인 가능)
        val localFile = File(filesDir, "hello.txt")
// getFile 호출 이후 다운로드 시작
        val downloadTask = ref.getFile(localFile)
// 반환받은 Task 객체를 통해서 콜백 메서드 등록 가능
        downloadTask.addOnSuccessListener {
// 성공 후 로직
            Log.d("mytag", "다운로드 성공 (총 다운로드 바이트 : ${it.totalByteCount})")
            val bytes = localFile.readBytes()
            for(b in bytes) Log.d("mytag", b.toString())
        }.addOnFailureListener {
// 실패 로직
            Log.d("mytag", "다운로드 실패 ${it.message}")
        }
        storageRef.child("hello.txt").downloadUrl.addOnSuccessListener {
// 파일 다운로드 가능한 웹 주소 출력
            Log.d("mytag", it.toString())
        }.addOnFailureListener {
            Log.d("mytag", "다운로드 URL 접근 실패")
        }


        storageRef.child("hello.txt").delete().addOnSuccessListener {
// it이 Void! 타입 값이므로 따로 정보는 전달되지 않음
            Log.d("mytag", "파일 삭제 성공")
        }.addOnFailureListener {
            Log.d("mytag", "파일 삭제 실패 ${it.message}")
        }
        val stream = FileInputStream(File("path/to/images/rivers.jpg"))
//        val cities = db.collection("cities")
//
//        val data1 = hashMapOf(
//            "name" to "San Francisco",
//            "state" to "CA",
//            "country" to "USA",
//            "capital" to false,
//            "population" to 860000,
//            "regions" to listOf("west_coast", "norcal")
//        )
//        cities.document("SF").set(data1)
//
//        val data2 = hashMapOf(
//            "name" to "Los Angeles",
//            "state" to "CA",
//            "country" to "USA",
//            "capital" to false,
//            "population" to 3900000,
//            "regions" to listOf("west_coast", "socal")
//        )
//        cities.document("LA").set(data2)
//
//        val data3 = hashMapOf(
//            "name" to "Washington D.C.",
//            "state" to null,
//            "country" to "USA",
//            "capital" to true,
//            "population" to 680000,
//            "regions" to listOf("east_coast")
//        )
//        cities.document("DC").set(data3)
//
//        val data4 = hashMapOf(
//            "name" to "Tokyo",
//            "state" to null,
//            "country" to "Japan",
//            "capital" to true,
//            "population" to 9000000,
//            "regions" to listOf("kanto", "honshu")
//        )
//        cities.document("TOK").set(data4)
//
//        val data5 = hashMapOf(
//            "name" to "Beijing",
//            "state" to null,
//            "country" to "China",
//            "capital" to true,
//            "population" to 21500000,
//            "regions" to listOf("jingjinji", "hebei")
//        )
//        cities.document("BJ").set(data5)

//        val docRef = db.collection("cities").document("SF")
//        docRef.get()
//            .addOnSuccessListener { document ->
//                if (document != null) {
//                    Log.d(TAG, "DocumentSnapshot data: ${document.data}")
//                } else {
//                    Log.d(TAG, "No such document")
//                }
//            }
//
//                //무조건 리스너가 있어야 함
//            .addOnFailureListener { exception ->
//                Log.d(TAG, "get failed with ", exception)
//            }
//    data class City(
//    val name: String? = null,
//    val state: String? = null,
//    val country: String? = null,
//    @field:JvmField // use this annotation if your Boolean field is prefixed with 'is'
//    val isCapital: Boolean? = null,
//    val population: Long? = null,
//    val regions: List<String>? = null
//)
//
//        val docRef = db.collection("cities").document("BJ")
//        docRef.get().addOnSuccessListener { documentSnapshot ->
//            val city = documentSnapshot.toObject<City>()
//            Log.d("mytag", city.toString())
//        }
//
//        db.collection("cities")
//            .whereEqualTo("capital", true)
//            .get()
//            .addOnSuccessListener { documents ->
//                for (document in documents) {
//                    Log.d(TAG, "${document.id} => ${document.data}")
//                }
//            }
//            .addOnFailureListener { exception ->
//                Log.w(TAG, "Error getting documents: ", exception)
//            }

//
//        val city = City("Los Angeles", "CA", "USA",
//            false, 5000000L, listOf("west_coast", "socal"))
//        db.collection("cities").document("LA").set(city)

// Add a new document with a generated id.
//        val data = hashMapOf(
//            "name" to "Tokyo",
//            "country" to "Japan"
//        )

//        db.collection("cities")
//            .add(data)
//            .addOnSuccessListener { documentReference ->
//                Log.d("mytag", "DocumentSnapshot written with ID: ${documentReference.id}")
//            }
//            .addOnFailureListener { e ->
//                Log.w("mytag", "Error adding document", e)
//            }

//        val data = HashMap<String, Any>()
//
//        val newCityRef = db.collection("cities").document()
//
//// Later...
//        newCityRef.set(data)

//        val docData = hashMapOf(
//            "stringExample" to "Hello world!",
//            "booleanExample" to true,
//            "numberExample" to 3.14159265,
//            "dateExample" to Timestamp(Date()),
//            "listExample" to arrayListOf(1, 2, 3),
//            "nullExample" to null
//        )
//
//        val nestedData = hashMapOf(
//            "a" to 5,
//            "b" to true
//        )
//
//        docData["objectExample"] = nestedData
//
//        db.collection("data").document("one")
//            .set(docData)
//            .addOnSuccessListener {
//                Log.d("mytag", "DocumentSnapshot successfully written!")
//            }
//            .addOnFailureListener {
//                e -> Log.w("mytag", "Error writing document", e)
//            }

//        val city = hashMapOf(
//            "name" to "Los Angeles",
//            "state" to "CA",
//            "country" to "USA"
//        )
//
//        db.collection("cities").document("LA")
//            .set(city)
//            .addOnSuccessListener { Log.d("mytag", "DocumentSnapshot successfully written!") }
//                .addOnFailureListener { e -> Log.w("mytag", "Error writing document", e) }
    }
}