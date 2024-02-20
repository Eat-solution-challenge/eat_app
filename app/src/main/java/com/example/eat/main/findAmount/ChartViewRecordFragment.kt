import android.os.Bundle
import androidx.fragment.app.Fragment // Fragment를 import해야 합니다.

class ChartViewRecordFragment : Fragment() {

    // newInstance 메서드 정의
    companion object {
        fun newInstance(menuName: String, amount: String, createDate: String, mainCategory: Int): ChartViewRecordFragment {
            val fragment = ChartViewRecordFragment()
            val args = Bundle()
            args.putString("menuName", menuName)
            args.putString("amount", amount)
            args.putString("createDate", createDate)
            args.putInt("mainCategory", mainCategory)
            fragment.arguments = args
            return fragment
        }
    }

    // 나머지 Fragment 코드…
}