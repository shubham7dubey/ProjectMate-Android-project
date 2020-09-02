import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.project3.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class messageAdapter extends RecyclerView.Adapter<messageAdapter.messageViewHolder> {
    private List<String> userMessageList;
    public messageAdapter(List<String> userMessageList)
    {
        this.userMessageList=userMessageList;
    }

    @NonNull
    @Override
    public messageAdapter.messageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull messageAdapter.messageViewHolder messageViewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class messageViewHolder extends RecyclerView.ViewHolder{
        public TextView senderMessage,receiverMessage;
        public CircleImageView senderImg;

        public messageViewHolder(@NonNull View itemView) {
            super(itemView);

            senderMessage=(TextView)itemView.findViewById(R.id.sender_message_text);
            receiverMessage=(TextView)itemView.findViewById(R.id.receiver_message_text);
            senderImg=(CircleImageView)itemView.findViewById(R.id.message_profile_image);

        }
    }
}
