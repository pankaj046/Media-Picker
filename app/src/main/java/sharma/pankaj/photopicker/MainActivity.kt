package sharma.pankaj.photopicker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts

class MainActivity : AppCompatActivity() {

    private var mediaType = "image"
    var mimeType = "image/png"
    lateinit var spinner: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        spinner = findViewById(R.id.mimeTypeSpinner)

        findViewById<Button>(R.id.button).setOnClickListener {
            when(mediaType){
                "image" -> pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                "video" -> pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.VideoOnly))
                "imageVideo" -> pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageAndVideo))
                "mimeType" -> pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.SingleMimeType(mimeType)))
            }
        }

        findViewById<RadioGroup>(R.id.mediaTypeSelector).setOnCheckedChangeListener { group, checkedId ->
            when(checkedId){
                R.id.image -> {
                    spinner.visibility = View.GONE
                    mediaType = "image"
                }
                R.id.video ->  {
                    spinner.visibility = View.GONE
                    mediaType = "video"
                }
                R.id.imageVideo ->  {
                    spinner.visibility = View.GONE
                    mediaType = "imageVideo"
                }
                R.id.mimeType -> {
                    spinner.visibility = View.VISIBLE
                    mediaType = "mimeType"
                }
            }
        }

        ArrayAdapter.createFromResource(
            this,
            R.array.mime_type,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                mimeType = parent?.getItemAtPosition(position).toString()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }


    private val pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        if (uri != null) {
            Toast.makeText(this, "$uri", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Media file not selected", Toast.LENGTH_SHORT).show()
        }
    }
}