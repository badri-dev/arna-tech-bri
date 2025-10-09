import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection

class TopArcShape(
    private val arcWidth: Float = 200f,  // lebar lengkungan
    private val arcHeight: Float = 90f, // tinggi lengkungan
    private val cornerRadius: Float = 56f
) : Shape {

    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val path = Path().apply {
            val w = size.width
            val h = size.height
            val centerX = w / 2f

            // ⤵️ mulai dari kiri atas
            moveTo(0f, cornerRadius)
            quadraticBezierTo(0f, 0f, cornerRadius, 0f)

            // garis ke sebelum lengkungan tengah
            lineTo(centerX - arcWidth / 2, 0f)

            // buat lengkungan ke bawah (cekung)
            quadraticBezierTo(
                centerX,
                arcHeight, // kedalaman lengkungan
                centerX + arcWidth / 2,
                0f
            )

            // lanjut ke kanan atas dan radius kanan
            lineTo(w - cornerRadius, 0f)
            quadraticBezierTo(w, 0f, w, cornerRadius)

            // sisi kanan & bawah
            lineTo(w, h)
            quadraticBezierTo(w, h, w, h)
            lineTo(cornerRadius, h)
            quadraticBezierTo(0f, h, 0f, h)

            close()
        }

        return Outline.Generic(path)
    }
}
